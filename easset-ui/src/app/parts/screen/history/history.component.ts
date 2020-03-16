import { Component, ViewChild, Output, EventEmitter, Input, OnChanges } from '@angular/core';
import { HistService } from 'src/app/services/api/hist.service';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { BulkUpdateHist } from 'src/app/models/api/hist/bulk-update-hist';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent implements OnChanges {
  /**
   * 履歴リスト
   */
  dataHistList;

  /**
   * 情報フォーム
   */
  infoForm: FormGroup;

  /**
   * オブジェクトラベル
   */
  objLabel = {
    labelLicenseId: '', labelAssetId: '', labelAppID: '', labelVer: '', labelApStatus: '', labelApGetType: '',
    labelApDate: '', labelApDate2: '', labelApproveDate: '', labelCheckbox: '', labelSpecDate: '',
    labelApGetAmount: '', labelCheckbox2: '', labelSpecDate2: '', labelRegistAppId: '', labelDeleteDate: '',
    labelContractNum: '', labelDreamsNum: '', labelShisanNum: '', labelParentAssetId: ''
  };

  /**
   * 要素の隠し変数
   */
  deleteFlag = '';
  apGetType = '';

  /**
   * クラスを拡張する
   */
  keyClass: string;

  /**
   * 履歴一覧を検索するエンティティ(テーブル)名
   */
  @Input() entityName: string;

  /**
   * パラメータ
   */
  @Input() param;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<boolean> = new EventEmitter();

  /**
   * グリッド Ref
   */
  @ViewChild('gridDataHistList', null) gridDataHistList: EaFlexGrid;

  constructor(
              private histService: HistService,
              private messageService: MessageService,
              private fb: FormBuilder,
              private datePipe: DatePipe) {
    this.infoForm = this.fb.group({
      applicationId: [''],
      apStatus: [''],
      apStatusName: [''],
      apDate: [''],
      approveDate: [''],
      assetId: [''],
      specialApproveFlag: [true],
      specialApproveDate: [''],
      apGetTypeName: [''],
      apGetAmountRangeName: [''],
      licenseId: [''],
      applicationVersion: [''],
      registApplicationId: [''],
      deleteDate: [''],
      contractNum: [''],
      contractEda: [''],
      dreamsNum: [''],
      shisanNum: [''],
      parentAssetId: [''],
    });
  }

  /**
   * コンポーネント初期処理
   */
  ngOnChanges() {
    if (this.param === undefined) {
      return;
    }
    this.deleteFlag = this.param.deleteFlag !== undefined ? this.param.deleteFlag : '';
    this.apGetType = this.param.apGetType !== undefined ? this.param.apGetType : '';
    this.initForm(this.param);
    switch (this.entityName) {
      case 'AP_ASSET':
        this.keyClass = 'ap-asset';
        this.objLabel.labelAssetId = '登録申請番号:';
        this.objLabel.labelAppID = '取得申請番号:';
        this.objLabel.labelApStatus = 'ステータス:';
        this.objLabel.labelApDate2 = '申請日:';
        this.getHistList(this.entityName, this.param.assetId);
        break;
      case 'AP_BGN_INT':
        this.keyClass = 'ap-bgn-int';
        this.objLabel.labelAppID = '登録申請番号:';
        this.objLabel.labelApStatus = 'ステータス:';
        this.objLabel.labelApDate = '申請日:';
        this.objLabel.labelApproveDate = '承認日:';
        this.getHistList(this.entityName, this.param.applicationId);
        break;
      case 'AP_CHANGE':
        this.keyClass = 'ap-change';
        this.objLabel.labelAppID = '移動申請書番号:';
        this.objLabel.labelApStatus = 'ステータス:';
        this.objLabel.labelApDate = '申請日:';
        this.getHistList(this.entityName, this.param.applicationId);
        break;
      case 'AP_END_LE':
        this.keyClass = 'ap-end-le';
        this.objLabel.labelAppID = '申請書番号:';
        this.objLabel.labelApStatus = 'ステータス:';
        this.objLabel.labelApDate = '申請日:';
        this.objLabel.labelCheckbox = '稟議書・経営会議等承認済';
        this.objLabel.labelSpecDate = '承認日:';
        this.getHistList(this.entityName, this.param.applicationId);
        break;
      case 'AP_END_RE':
        this.keyClass = 'ap-end-re';
        this.objLabel.labelAppID = '申請書番号:';
        this.objLabel.labelApStatus = 'ステータス:';
        this.objLabel.labelApDate = '申請日:';
        this.objLabel.labelCheckbox = '稟議書・経営会議等承認済';
        this.objLabel.labelSpecDate = '承認日:';
        this.getHistList(this.entityName, this.param.applicationId);
        break;
      case 'AP_END_TAN':
        this.keyClass = 'ap-end-tan';
        this.objLabel.labelAppID = '除売却申請書番号:';
        this.objLabel.labelApStatus = 'ステータス:';
        this.objLabel.labelApDate = '申請日:';
        this.objLabel.labelCheckbox = '稟議書・経営会議等承認済';
        this.objLabel.labelSpecDate = '承認日:';
        this.getHistList(this.entityName, this.param.applicationId);
        break;
      case 'AP_GET_INT':
        this.keyClass = 'ap-get-int';
        this.objLabel.labelAppID = '取得申請番号:';
        this.objLabel.labelVer = 'Ver.';
        this.objLabel.labelApStatus = 'ステータス:';
        this.objLabel.labelApGetType = '取得形態:';
        this.objLabel.labelApDate = '申請日:';
        this.objLabel.labelApGetAmount = '取得金額:';
        this.objLabel.labelCheckbox2 = '稟議書・経営会議等承認済';
        this.objLabel.labelSpecDate2 = '承認日:';
        this.getHistList(this.param.applicationId, this.param.applicationVersion);
        break;
      case 'AP_GET_TAN':
        this.keyClass = 'ap-get-tan';
        this.objLabel.labelAppID = '取得申請番号:';
        this.objLabel.labelApStatus = 'ステータス:';
        this.objLabel.labelApGetType = '取得形態:';
        this.objLabel.labelApDate = '申請日:';
        this.objLabel.labelApGetAmount = '取得金額:';
        this.objLabel.labelCheckbox2 = '稟議書・経営会議等承認済';
        this.objLabel.labelSpecDate2 = '承認日:';
        this.getHistList(this.entityName, this.param.applicationId);
        break;
      case 'AP_LICENSE':
        this.keyClass = 'ap-license';
        this.objLabel.labelLicenseId = '登録申請番号:';
        this.objLabel.labelAppID = '取得申請番号:';
        this.objLabel.labelApStatus = 'ステータス:';
        this.objLabel.labelApDate = '申請日:';
        this.getHistList(this.entityName, this.param.licenseId);
        break;
      case 'ASSET':
        this.keyClass = 'asset';
        this.objLabel.labelAssetId = '情報機器管理番号:';
        this.objLabel.labelDeleteDate = '抹消日:';
        this.objLabel.labelRegistAppId = '登録申請書番号:';
        this.objLabel.labelAppID = '取得申請書番号:';
        this.objLabel.labelContractNum = '契約番号:';
        this.objLabel.labelDreamsNum = 'DREAMS番号:';
        this.objLabel.labelShisanNum = '資産番号:';
        this.objLabel.labelParentAssetId = '親情報機器管理番号:';
        this.getHistList(this.entityName, this.param.assetId);
        break;
      case 'LICENSE':
        this.keyClass = 'license';
        this.objLabel.labelLicenseId = 'ライセンス管理番号:';
        this.objLabel.labelDeleteDate = '抹消日:';
        this.objLabel.labelRegistAppId = '登録申請書番号:';
        this.objLabel.labelAppID = '取得申請番号:';
        this.objLabel.labelContractNum = '契約番号:';
        this.objLabel.labelShisanNum = '資産番号:';
        this.getHistList(this.entityName, this.param.licenseId);
        break;
      case 'INT_EXT':
        this.keyClass = 'int-ext';
        this.objLabel.labelAppID = '取得申請書番号:';
        this.getHistList(this.param.applicationId, this.param.applicationVersion);
        break;
    }
  }

  /**
   * 初期処理
   * @param data: object 画面オープンパラメータ
   */
  initForm(data) {
    this.infoForm.patchValue({
      applicationId: data.applicationId !== undefined ? data.applicationId : '',
      apStatus: data.apStatus !== undefined ? data.apStatus : '',
      apStatusName: data.apStatusName !== undefined ? data.apStatusName : '',
      apDate: data.apDate !== undefined ? this.datePipe.transform(data.apDate, 'yyyy/MM/dd') : '',
      approveDate: data.approveDate !== undefined ? this.datePipe.transform(data.approveDate, 'yyyy/MM/dd') : '',
      assetId: data.assetId !== undefined ? data.assetId : '',
      specialApproveFlag: data.specialApproveFlag === '1' ? true : (data.specialApproveFlag === '2' ? true : false),
      specialApproveDate: data.specialApproveDate !== undefined ? data.specialApproveDate : '',
      apGetTypeName: data.apGetTypeName !== undefined ? data.apGetTypeName : '',
      apGetAmountRangeName: data.apGetAmountRangeName !== undefined ? data.apGetAmountRangeName : '',
      licenseId: data.licenseId !== undefined ? data.licenseId : '',
      applicationVersion: data.applicationVersion !== undefined ? data.applicationVersion : '',
      registApplicationId: data.registApplicationId !== undefined ? data.registApplicationId : '',
      deleteDate: data.deleteDate !== undefined ? this.datePipe.transform(data.deleteDate, 'yyyy/MM/dd') : '',
      contractNum: data.contractNum !== undefined ? data.contractNum : '',
      contractEda: data.contractEda !== undefined ? data.contractEda : '',
      dreamsNum: data.dreamsNum !== undefined ? data.dreamsNum : '',
      shisanNum: data.shisanNum !== undefined ? data.shisanNum : '',
      parentAssetId: data.parentAssetId !== undefined ? data.parentAssetId : '',
    });
  }

  /**
   * APIを呼び出して履歴リストを取得する
   */
  getHistList(data1, data2) {
    this.histService.getHistList(data1, data2).subscribe(
      (resp: BulkUpdateHist[]) => {
        if (!resp.length) {
          this.messageService.warn(SystemMessage.Warn.msg200002);
        }
        this.dataHistList = resp;
        this.gridDataHistList.resetSelection();
      }
    );
  }

  /**
   * 選択した行を取得する
   */
  selectedData() {
    const data = this.gridDataHistList.selectedItems[0];
    if (data === undefined) {
      this.messageService.err(SystemMessage.Err.msg300002);
      return null;
    } else {
      return data;
    }
  }
}
