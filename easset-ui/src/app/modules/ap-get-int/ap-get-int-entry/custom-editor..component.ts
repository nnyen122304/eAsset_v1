import * as wjGrid from 'wijmo/wijmo.grid';
import * as wjcCore from 'wijmo/wijmo';
import * as wjcInput from 'wijmo/wijmo.input';

export class CustomGridEditor {
  grid: wjGrid.FlexGrid;
  col: wjGrid.Column;
  ctl: wjGrid.FlexGrid;
  openDropDown: boolean;
  rng: wjGrid.CellRange;

  isEditing: boolean; // true if any custom editor is active

  /**
   * Initializes a new instance of a CustomGridEditor.
   */
  // tslint:disable-next-line: no-any
  constructor(flex: wjGrid.FlexGrid, binding: string, edtClass: any, options: any) {

    // save references
    this.grid = flex;
    this.col = flex.columns.getColumn(binding);

    // create editor
    this.ctl = new edtClass(document.createElement('div'), options);

    // connect grid events
    flex.beginningEdit.addHandler(this._beginningEdit, this);
    flex.sortingColumn.addHandler(() => {
      this._commitRowEdits();
    });
    flex.scrollPositionChanged.addHandler(() => {
      if (this.ctl.containsFocus()) {
        flex.focus();
      }
    });
    flex.selectionChanging.addHandler((s, e: wjGrid.CellRangeEventArgs) => {
      if (e.row !== s.selection.row) {
        this._commitRowEdits();
      }
    });

    // connect editor events
    this.ctl.addEventListener(this.ctl.hostElement, 'keydown', (e: KeyboardEvent) => {
      switch (e.keyCode) {
        case wjcCore.Key.Tab:
        case wjcCore.Key.Enter:
          e.preventDefault(); // TFS 255685
          this._closeEditor(true);
          this.grid.focus();

          // forward event to the grid so it will move the selection
          // tslint:disable-next-line: no-any
          const evt = document.createEvent('HTMLEvents') as any;
          evt.initEvent('keydown', true, true);
          'altKey,metaKey,ctrlKey,shiftKey,keyCode'.split(',').forEach((prop) => {
            evt[prop] = e[prop];
          });
          this.grid.hostElement.dispatchEvent(evt);
          break;

        case wjcCore.Key.Escape:
          this._closeEditor(false);
          this.grid.focus();
          break;
      }
    });

    // close the editor when it loses focus
    this.ctl.lostFocus.addHandler(() => {
      setTimeout(() => { // Chrome/FireFox need a timeOut here... (TFS 138985)
        if (!this.ctl.containsFocus()) {
          this._closeEditor(true); // apply edits and close editor
          this.grid.onLostFocus(); // commit item edits if the grid lost focus
        }
      });
    });

    // commit edits when grid loses focus
    this.grid.lostFocus.addHandler(() => {
      setTimeout(() => { // Chrome/FireFox need a timeOut here... (TFS 138985)
        if (!this.grid.containsFocus() && !this.isEditing) {
          this._commitRowEdits();
        }
      });
    });

    // open drop-down on f4/alt-down
    this.grid.addEventListener(this.grid.hostElement, 'keydown', (e: KeyboardEvent) => {

      // open drop-down on f4/alt-down
      this.openDropDown = false;
      if (e.keyCode === wjcCore.Key.F4 ||
        (e.altKey && (e.keyCode === wjcCore.Key.Down || e.keyCode === wjcCore.Key.Up))) {
        const colIndex = this.grid.selection.col;
        if (colIndex > -1 && this.grid.columns[colIndex] === this.col) {
          this.openDropDown = true;
          this.grid.startEditing(true);
          e.preventDefault();
        }
      }

      // commit edits on Enter (in case we're at the last row, TFS 268944)
      if (e.keyCode === wjcCore.Key.Enter) {
        this._commitRowEdits();
      }
    }, true);

    // close editor when user resizes the window
    // REVIEW: hides editor when soft keyboard pops up (TFS 326875)
    window.addEventListener('resize', () => {
      if (this.ctl.containsFocus()) {
        this._closeEditor(true);
        this.grid.focus();
      }
    });
  }

  // gets an instance of the control being hosted by this grid editor
  get control() {
    return this.ctl;
  }

  // handle the grid's beginningEdit event by canceling the built-in editor,
  // initializing the custom editor and giving it the focus.
  _beginningEdit(grid: wjGrid.FlexGrid, args: wjGrid.CellRangeEventArgs) {

    // check that this is our column
    if (grid.columns[args.col] !== this.col) {
      return;
    }

    // check that this is not the Delete key
    // (which is used to clear cells and should not be messed with)
    const evt = args.data;
    if (evt && evt.keyCode === wjcCore.Key.Delete) {
      return;
    }

    // cancel built-in editor
    args.cancel = true;

    // save cell being edited
    this.rng = args.range;
    this.isEditing = true;

    // initialize editor host
    const rcCell = grid.getCellBoundingRect(args.row, args.col);
    const rcBody = document.body.getBoundingClientRect();
    const ptOffset = new wjcCore.Point(-rcBody.left, -rcBody.top);
    const zIndex = (args.row < grid.frozenRows || args.col < grid.frozenColumns) ? '3' : '';
    wjcCore.setCss(this.ctl.hostElement, {
      position: 'absolute',
      left: rcCell.left - 1 + ptOffset.x,
      top: rcCell.top - 1 + ptOffset.y,
      width: rcCell.width + 1,
      height: grid.rows[args.row].renderHeight + 1,
      borderRadius: '0px',
      zIndex, // TFS 291852
    });

    // initialize editor content
    if (!wjcCore.isUndefined(this.ctl['text'])) {
      this.ctl['text'] = grid.getCellData(this.rng.row, this.rng.col, true);
    } else {
      throw new Error('Can\'t set editor value/text...');
    }

    // start editing item
    const ecv = grid.editableCollectionView;
    const item = grid.rows[args.row].dataItem;
    if (ecv && item && item !== ecv.currentEditItem) {
      setTimeout(() => {
        grid.onRowEditStarting(args);
        ecv.editItem(item);
        grid.onRowEditStarted(args);
      }, 50); // wait for the grid to commit edits after losing focus
    }

    // activate editor
    document.body.appendChild(this.ctl.hostElement);
    this.ctl.focus();
    setTimeout(() => {

      // get the key that triggered the editor
      const key = (evt && evt.charCode > 32)
        ? String.fromCharCode(evt.charCode)
        : null;

      // get input element in the control
      const input = this.ctl.hostElement.querySelector('input') as HTMLInputElement;

      // send key to editor
      if (input) {
        if (key) {
          input.value = key;
          wjcCore.setSelectionRange(input, key.length, key.length);
          const evtInput = document.createEvent('HTMLEvents');
          evtInput.initEvent('input', true, false);
          input.dispatchEvent(evtInput);
        } else {
          input.select();
        }
      }

      // give the control focus
      if (!input && !this.openDropDown) {
        this.ctl.focus();
      }

      // open drop-down on F4/alt-down
      if (this.openDropDown && this.ctl instanceof wjcInput.DropDown) {
        this.ctl.isDroppedDown = true;
        this.ctl.dropDown.focus();
      }

    }, 50);
  }

  // close the custom editor, optionally saving the edits back to the grid
  _closeEditor(saveEdits: boolean) {
    if (this.rng) {
      const flexGrid = this.grid;
      const ctl = this.ctl;
      const host = ctl.hostElement;

      // raise grid's cellEditEnding event
      const e = new wjGrid.CellEditEndingEventArgs(flexGrid.cells, this.rng);
      flexGrid.onCellEditEnding(e);

      // save editor value into grid
      if (saveEdits) {
        if (!wjcCore.isUndefined(ctl['value'])) {
          this.grid.setCellData(this.rng.row, this.rng.col, ctl['value']);
        } else if (!wjcCore.isUndefined(ctl['text'])) {
          this.grid.setCellData(this.rng.row, this.rng.col, ctl['text']);
        } else {
          throw new Error('Can\'t get editor value/text...');
        }
        this.grid.invalidate();
      }

      // close editor and remove it from the DOM
      if (ctl instanceof wjcInput.DropDown) {
        ctl.isDroppedDown = false;
      }
      host.parentElement.removeChild(host);
      this.rng = null;
      this.isEditing = false;

      // raise grid's cellEditEnded event
      flexGrid.onCellEditEnded(e);
    }
  }

  // commit row edits, fire row edit end events (TFS 339615)
  _commitRowEdits() {
    const flexGrid = this.grid;
    const ecv = flexGrid.editableCollectionView;
    this._closeEditor(true);
    if (ecv && ecv.currentEditItem) {
      const e = new wjGrid.CellEditEndingEventArgs(flexGrid.cells, flexGrid.selection);
      ecv.commitEdit();
      setTimeout(() => { // let cell edit events fire first
        flexGrid.onRowEditEnding(e);
        flexGrid.onRowEditEnded(e);
        flexGrid.invalidate();
      });
    }
  }
}
