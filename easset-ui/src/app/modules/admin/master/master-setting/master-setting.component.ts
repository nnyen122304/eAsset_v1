import { Component, ViewChild, OnInit, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DatePipe } from '@angular/common';

import * as wjGrid from 'wijmo/wijmo.grid';
import * as wjGridFilter from 'wijmo/wijmo.grid.filter';

import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { AdminComponent } from 'src/app/modules/admin/admin.component';
import { InputDetailComponent } from './input-detail/input-detail.component';

import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';
import { FileDownloadService } from 'src/app/services/api/file-download.service';

import { SystemConst } from 'src/app/const/system-const';
import { DeepCopy } from 'src/app/utils/deep-copy';
import { toMultibyte } from 'src/app/utils/unibyte-multibyte';

import { SessionInfo } from 'src/app/models/session-info';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { CodeName } from 'src/app/models/api/code-name';
import { AdmCodeNameFilter } from 'src/app/models/admin/adm-code-name-filter';
import { CodeNameSet } from 'src/app/models/api/code-name-set';
import { CodeNameSetItem } from 'src/app/models/api/code-name-set-item';
import { SystemMessage } from 'src/app/const/system-message';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';


/**
 * 汎用マスタ設定画面コンポネント
 */

@Component({
  selector: 'app-master-setting',
  templateUrl: './master-setting.component.html',
  styleUrls: ['./master-setting.component.scss']
})
export class MasterSettingComponent extends AbstractChildComponent<AdminComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 絞込フォーム
   */
  filterForm: FormGroup;

  /**
   * Selected Item from 汎用マスタ管理 view
   */
  item: CodeNameSet;

  /**
   * 定義一覧情報
   */
  defList: CodeNameSetItem[];

  /**
   * 会社一覧情報
   */
  companyList: CodeName[];

  /**
   * マスタ項目一覧情報
   */
  masterList: CodeName[];

  /**
   * 絞込用マスタ一覧情報
   */
  filterList: AdmCodeNameFilter[];

  /**
   * マスタ詳細表示されているか判定用
   */
  isInputDetailShown = false;

  /**
   * 親マスタが存在するか判定用
   */
  hasParentMaster = false;

  /**
   * 順番指定用値
   */
  lastOrder = 0;

  /**
   * 棚卸一覧グリッド Ref
   */
  @ViewChild('gridAdminMasterSetting', null) gridAdminMasterSetting: EaFlexGrid;

  /**
   * マスタ詳細コンポネント
   */
  @ViewChild(InputDetailComponent, null) inputDetail: InputDetailComponent;

  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private elementRef: ElementRef,
    private sessionService: SessionService,
    private messageService: MessageService,
    private masterService: MasterService,
    private fileDownloadService: FileDownloadService
  ) {
    super();
    this.fb = fb;
  }

  /**
   * 画面読み込み
   */
  ngOnInit() {
    this.initForms();
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'initMasterSetting') {
        this.item = param.params.item;
        this.hasParentMaster = param.params.item.parentCategoryCode !== null ? true : false;
        this.init();
      }
    });
  }

  /**
   * 初期処理
   */
  init() {

    const categoryCode = this.item.categoryCode;
    const companyCode = this.item.setCompanyCode === '000' ? this.sessionInfo.loginCompanyCode : null;

    this.masterService.getCodeNameSetItemList(categoryCode).subscribe(
      (resp) => {
        this.defList = resp.sort((a, b) => a.itemSeq - b.itemSeq);
        this.initFilterList();
      }
    );

    this.masterService.searchCodeNameList('USE_COMPANY', true).subscribe(
      (resp) => {
        this.companyList = resp;
      }
    );

    this.masterService.searchCodeNameList(categoryCode, false, null, companyCode).subscribe(
      (resp) => {
        this.masterList = resp;
        this.lastOrder = this.masterList.reduce((prev, cur) => (prev.sortNumber > cur.sortNumber) ? prev : cur).sortNumber;
        if (this.gridAdminMasterSetting) {
          this.gridAdminMasterSetting.resetSelection();
        }
      }
    );
  }

  /**
   * フォーム初期設定
   */
  initForms() {
    this.filterForm = this.fb.group({
      filterId: [''],
      filterValue: [''],
      parentMasterName: [''],
      parentMasterCode: [''],
      parentCategoryCode: ['']
    });
  }

  /**
   * 絞込初期処理
   */
  initFilterList() {
    this.filterList = [
      {id: 'internalCode', name: 'コード'},
      {id: 'deleteFlagName', name: '停止'}
    ];
    if (this.item.setCompanyCode !== '000') {
      this.filterList.push({id: 'companyName', name: '会社'});
    }
    for (const item of this.defList) {
      this.filterList.push({id: 'value' + item.itemSeq, name: item.itemName});
    }
  }

  /**
   * グリッド初期処理
   */
  onGridInitialized() {
    let frozenColumns = 3;
    if (this.item.lineDelEnableFlag === '1') {
      frozenColumns++;
    }
    if (this.hasParentMaster) {
      frozenColumns++;
    }
    if (this.item.setCompanyCode !== '000') {
      frozenColumns++;
    }
    this.gridAdminMasterSetting.frozenColumns = frozenColumns;
    this.gridAdminMasterSetting.cellEditEnding.addHandler((s: EaFlexGrid, e: wjGrid.CellEditEndingEventArgs) => {
      this.sessionService.setDataChange(true);
    });
  }

  /**
   * コンボボックス値取得
   * @param defData 指定データ
   * @param value 値
   */
  getCmbValue(defData: CodeNameSetItem, value: number) {
    if (!defData || !value) {
      return;
    }
    const cmbDataArr = defData.cmbData.split(' ');
    const cmbLabelArr = defData.cmbLabel.split(' ');
    const dataIndex = cmbDataArr.indexOf(value.toString());
    if (dataIndex !== -1) {
      const labelIndex = cmbLabelArr.indexOf(cmbLabelArr[dataIndex]);
      if (labelIndex !== -1) {
        return cmbLabelArr[labelIndex];
      }
    }
  }

  /**
   * 更新処理
   */
  update() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const categoryCode: string = this.item.categoryCode;
    const companyCode = this.item.setCompanyCode === '000' ? this.sessionInfo.loginCompanyCode : null;
    this.sort();
    this.masterService.updateCodeNameList(loginStaffCode, categoryCode, this.masterList, companyCode).subscribe(
      (resp: CodeName[]) => {
        this.isInputDetailShown = false;
        this.inputDetail.close();
        this.gridAdminMasterSetting.resetSelection();
        this.messageService.info(SystemMessage.Info.msg100003);
        this.sessionService.setDataChange(false);
        this.init();
      }
    );
  }

  /**
   * 並び替え
   */
  sort() {
    const sortedArr: CodeName[] = DeepCopy(this.gridAdminMasterSetting.itemsSource.sort((a: CodeName, b: CodeName) => a.sortNumber - b.sortNumber));
    this.masterList = sortedArr;
    this.gridAdminMasterSetting.collectionView.refresh();
    this.gridAdminMasterSetting.resetSelection();
  }

  /**
   * 絞り込み
   */
  filter() {

    const filterId = this.filterForm.controls.filterId.value;
    const filterValue = this.filterForm.controls.filterValue.value;
    const masterName = this.filterForm.controls.parentMasterName.value;

    const filter = new wjGridFilter.FlexGridFilter(this.gridAdminMasterSetting);
    filter.clear();
    filter.showFilterIcons = false;

    const noResultObject = {};
    noResultObject[SystemConst.Identifiers.filterString] = true;

    if (masterName) {
      const masterFilter = filter.getColumnFilter(this.gridAdminMasterSetting.columns.getColumn('parentInternalName'));
      masterFilter.valueFilter.showValues = this.masterList
      .filter((obj: CodeName) => {
        if (masterName) {
          if (obj.parentInternalName && obj.parentInternalName === masterName) {
            return true;
          }
        } else {
          return true;
        }
      })
      .map(v => {
        const ret = new Object();
        ret[masterName] = true;
        return ret;
      })
      .reduce((v1, v2) => {
        return Object.assign(v1, v2);
      }, noResultObject);
    }

    if (filterValue) {
      const columnFilter = filter.getColumnFilter(this.gridAdminMasterSetting.columns.getColumn(filterId));
      columnFilter.valueFilter.showValues = this.masterList
      .filter((obj: CodeName) => {
        if (filterValue.length > 0) {
          if (obj[filterId]) {
            // Check Text, Num, Textarea
            const includeValue = obj[filterId].toUpperCase().includes(filterValue.toUpperCase());
            const includeMultibyte = obj[filterId].toUpperCase().includes(toMultibyte(filterValue.toUpperCase()));
            if (obj[filterId] && (includeValue || includeMultibyte)) {
              return true;
            }
            // Check CMB data
            if (filterId.substring(0, 5) === 'value') {
              const valueNum = parseInt(filterId.replace('value', ''), 10);
              if (this.defList[valueNum - 1].itemInputType === 'CMB') {
                const rowCmbValue = this.getCmbValue(this.defList[valueNum - 1], obj[filterId]);
                if (rowCmbValue) {
                  if (
                    rowCmbValue.toUpperCase().includes(filterValue.toUpperCase()) ||
                    rowCmbValue.toUpperCase().includes(toMultibyte(filterValue.toUpperCase()))
                  ) {
                    return true;
                  }
                }

              }
            }
          }
        } else {
          return true;
        }
      })
      .map(v => {
        const ret = new Object();
        const key = (v[filterId]) ? v[filterId] : '';
        ret[key] = true;
        return ret;
      })
      .reduce((v1, v2) => {
        return Object.assign(v1, v2);
      }, noResultObject);
    }

    // フィルタ適用
    filter.apply();

  }

  /**
   * 詳細を表示
   * @param id マスタID
   * @param data マスタデータ
   */
  showInputDetails(id?: number, data?: CodeName) {
    this.lastOrder = this.masterList.reduce((prev, cur) => (prev.sortNumber > cur.sortNumber) ? prev : cur).sortNumber;
    if (id !== undefined && data) {
      this.inputDetail.open(this.lastOrder, id, data);
    } else {
      this.inputDetail.open(this.lastOrder);
    }
    this.isInputDetailShown = true;
    setTimeout(() => {
      this.updateLayout(true);
    });
  }

  /**
   * レイアウトを更新する
   * @param open 明細入力が開いているか認識用
   */
  updateLayout(open: boolean) {
    const component = this.elementRef.nativeElement.querySelector('#section-detail') as HTMLDivElement;
    const list = this.elementRef.nativeElement.querySelector('#grid-admin-master-setting') as HTMLDivElement;
    if (open) {
      const additionalMargin = 270 - component.clientHeight;
      list.style.height = 'calc(100vh - 466px + ' + additionalMargin + 'px)';
    } else {
      list.style.removeProperty('height');
    }
  }

  /**
   * マスタ詳細を非表示する
   * @param data データが存在するか判定用
   */
  closeInputDetail(data: boolean) {
    if (data) {
      this.isInputDetailShown = false;
      this.updateLayout(false);
      this.gridAdminMasterSetting.resetSelection();
    }
  }

  /**
   * マスタ項目選択後処理
   * @param e イベント情報
   */
  onMasterSelect(e: object) {
    if (wjGrid.CellType[this.gridAdminMasterSetting.hitTest(e).cellType]  !== 'Cell') {
      return;
    }
    if (this.item.lineAddEnableFlag === '1' && this.gridAdminMasterSetting.hitTest(e).panel.columns[this.gridAdminMasterSetting.hitTest(e).col].binding === 'sortNumber') {
      return;
    }
    if (this.gridAdminMasterSetting.hitTest(e).panel.columns[this.gridAdminMasterSetting.hitTest(e).col].name !== 'columnDelete') {
      const id = this.gridAdminMasterSetting.selectedRows[0]._idx;
      const data = DeepCopy(this.gridAdminMasterSetting.selectedRows[0].dataItem);
      this.inputDetail.enableSubmit();
      this.showInputDetails(id, data);
    }
  }

  /**
   * データ更新イベント処理
   */
  onDataUpdated(data: CodeName, id: number) {
    this.isInputDetailShown = false;
    if (id !== undefined) {
      this.masterList[id] = data;
      this.sessionService.setDataChange(true);
    } else {
      this.masterList.push(data);
    }
    this.gridAdminMasterSetting.resetSelection();
    this.gridAdminMasterSetting.collectionView.refresh();
    this.updateLayout(false);
  }

  /**
   * マスタ項目削除
   * @param index 行インデックス
   */
  removeMasterData(index: number) {
    const obj: CodeName = this.gridAdminMasterSetting.itemsSource[index];
    this.masterService.getCodeNameUseStatus(obj).subscribe(
      (resp: NonObjectResponse<string>) => {
        if (resp.value) {
          const messages = resp.value.split('$$$$$');
          this.messageService.err(messages);
        } else {
          if (this.gridAdminMasterSetting.selectedRows[0]._idx === index) {
            // Removing the editing master
            this.inputDetail.disableSubmit();
          }
          const updatedArr: CodeName[] = DeepCopy(this.gridAdminMasterSetting.itemsSource);
          updatedArr.splice(index, 1);
          this.gridAdminMasterSetting.itemsSource = updatedArr;
          this.masterList = updatedArr;
          this.gridAdminMasterSetting.resetSelection();
          this.sessionService.setDataChange(true);
        }
      }
    );
  }

  /**
   * CSVダウンロード
   */
  getCSV() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const categoryCode: string = this.item.categoryCode;
    const companyCode: string = this.sessionInfo.loginUser.companyCode;
    const setCompanyCode: string = this.item.setCompanyCode;
    this.masterService.createCodeNameCsv(loginStaffCode, categoryCode, companyCode, setCompanyCode).subscribe(
      (resp: NonObjectResponse<string>) => {
        const fileID = resp.value;
        const fileName = '汎用マスタ管理_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
        this.fileDownloadService.download(fileID, fileName, 'text/csv');
      }
    );
  }

  /**
   * 親マスタポップアップを開く
   */
  openParentMasterPopup() {

  }

  /**
   * 親選択後処理
   * @param data 親マスタ情報
   */
  onParentSelected(data: LovDataEx) {
  }

  /**
   * 汎用マスタ検索に戻る
   */
  close() {
    this.parent.changeChild(this.parent.viewIndexMaster, {action: 'backFromMasterSetting', params: {}});
  }

}
