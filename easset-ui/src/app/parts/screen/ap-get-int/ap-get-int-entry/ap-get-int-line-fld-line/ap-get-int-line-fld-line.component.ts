import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';

@Component({
  selector: 'app-ap-get-int-line-fld-line',
  templateUrl: './ap-get-int-line-fld-line.component.html',
  styleUrls: ['./ap-get-int-line-fld-line.component.scss']
})
export class ApGetIntLineFldLineComponent {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   *  value3CodeName
   */
  value3CodeName = 'value3 = "1"';

  FldLineForm: FormGroup;

  formAssetsEquipment: FormGroup;

  /**
   * 仕入先Form
   */
  formAstPurCompanyName: FormGroup;

  astTermFlagLef = false;

  /**
   * 分類(明細)
   */
  astCategoryTypeLef = '';

  /**
   * 行追加
   */
  @Input() btnAddFldLine = true;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,

  ) {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.formAssetsEquipment = this.fb.group({
      astAssetType: [''],
      astAssetTypeName: ['']
    });
    this.formAstPurCompanyName = this.fb.group({
      astPurCompanyCode: [''],
      astPurCompanyName: ['']
    });
    this.FldLineForm = this.fb.group({
      astTermFlagLef: [],
    });
   }

  addFldLine() {
    this.btnAddFldLine = !this.btnAddFldLine;
  }

  cancelFldLine() {
    this.btnAddFldLine = !this.btnAddFldLine;
  }
  astCategoryType(data) {
    this.astCategoryTypeLef = data.value2;
    console.log(data);
    console.log(data.value2);
  }
}
