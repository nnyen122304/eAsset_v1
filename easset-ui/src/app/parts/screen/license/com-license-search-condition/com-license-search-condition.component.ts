import { Component, OnInit, Input, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-com-license-search-condition',
  templateUrl: './com-license-search-condition.component.html',
  styleUrls: ['./com-license-search-condition.component.scss']
})
export class ComLicenseSearchConditionComponent implements OnInit, AfterViewInit {

  /**
   * form ソフトウェア
   */
  softwareForm: FormGroup;

  /**
   * form 取得・資産
   */
  licAssetForm: FormGroup;

  /**
   * form ライセンス形態
   */
  licLicenseForm: FormGroup;

  /**
   * form 保有・使用許諾
   */
  infoForm: FormGroup;

  /**
   * form 備考・付加情報
   */
  messageForm: FormGroup;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * アクセスレベル
   */
  accessLevel: string;

  /**
   *  要素の隠し変数
   */
  isLicSerialCode = true;
  isLicProductKey = true;
  isDscAttribute = true;

  /**
   * AttributePlural一覧
   */
  dscAttributePlural: string[] = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14',
    '15', '16', '17', '18', '19', '20'];

  /**
   * licHrdBandleFlagAC
   */
  // tslint:disable-next-line: no-any
  licHrdBandleFlagAC: any[];

  /**
   * licUpgradeFlagAC
   */
  // tslint:disable-next-line: no-any
  licUpgradeFlagAC: any[];

  /**
   * licTermFlagAC
   */
  // tslint:disable-next-line: no-any
  licTermFlagAC: any[];

  /**
   * アクセス権限
   */
  accessLevelSection = {
    ACCESS_LEVEL_SECTION_GENERAL: 'C',
    ACCESS_LEVEL_SECTION_MANAGER: 'B',
    ACCESS_LEVEL_ADMIN_COMPANY: 'A',
    ACCESS_LEVEL_ADMIN_SYSTEM: 'S',
  };

  /**
   * 権限設定オブジェクト
   */
  @Input() statesStatus;

  constructor(private fb: FormBuilder,
              private sessionService: SessionService, ) {
    this.licHrdBandleFlagAC = ([
      { data: '', label: '' },
      { data: '0', label: 'ハードウェアバンドル以外' },
      { data: '1', label: 'ハードウェアバンドル' },
    ]);
    this.licUpgradeFlagAC = ([
      { data: '', label: '' },
      { data: '0', label: 'ベースライセンス' },
      { data: '1', label: 'アップグレードライセンス' }
    ]);
    this.licTermFlagAC = ([
      { data: '', label: '' },
      { data: '0', label: 'タームライセンス以外' },
      { data: '1', label: 'タームライセンス' }
    ]);
  }

  /**
   * コンポネント初期処理
   */
  ngOnInit() {
    this.init();
  }

  /**
   * コンポネントビューの初期処理後
   */
  ngAfterViewInit(): void {
    setTimeout(() => {
      this.init();
    }, 500);
  }

  /**
   * 初期処理
   */
  init() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.accessLevel = this.sessionInfo.currentAccessLevel;
    this.softwareForm = this.fb.group({
      softwareMakerId: [''],
      softwareMakerName: [''],
      softwareId: [''],
      softwareName: [''],
    });

    this.licAssetForm = this.fb.group({
      licAssetTypeName: [''],
      licAssetType: [''],
      licGetTypeName: [''],
      licGetType: [''],
      licAmountFrom: [0],
      licAmountTo: [0],
      licSerialCode: [''],
      licSerialCodePlural: [''],
      licProductKey: [''],
      licProductKeyPlural: [''],
      licHrdBandleFlag: [''],
    });

    this.licLicenseForm = this.fb.group({
      licLicenseTypeName: [''],
      licLicenseType: [this.statesStatus.licLicenseType],
      licLicenseTypePlural: [this.statesStatus.licLicenseTypePlural],
      licUpgradeFlag: [this.statesStatus.licUpgradeFlag],
      licVolumeTypeName: [''],
      licVolumeType: [''],
      licTermFlag: [this.statesStatus.licTermFlag],
      trmEndDateFrom: [''],
      trmEndDateTo: [''],
    });

    this.infoForm = this.fb.group({
      holSectionCode: [''],
      holSectionName: [''],
      holCompanyName: [''],
      holCompanyCode: [''],
      holSectionYear: [''],
      allCompanyFlag: [this.statesStatus.allCompanyFlagSelected],
      includeSectionHierarchyFlag: [false],
      holStaffCode: [''],
      holStaffName: [''],
    });

    this.messageForm = this.fb.group({
      dscDescription: [''],
      dscAttribute: [''],
      dscAttributePlural: this.fb.array(this.dscAttributePlural.map((x) => '')),
    });

    if (this.statesStatus.allCompanyFlagEditLock) {
      this.infoForm.controls.allCompanyFlag.disable();
    }
    if (this.statesStatus.isLicTermFlagEditLock) {
      this.licLicenseForm.controls.licTermFlag.disable();
    }
    if (this.statesStatus.isLicUpgradeFlagEditLock) {
      this.licLicenseForm.controls.licUpgradeFlag.disable();
    }
    if (this.statesStatus.isLicUpgradeFlagEditLock) {
      this.licLicenseForm.controls.licUpgradeFlag.disable();
    }
    this.dataChange();
  }

  /**
   * 値変更ハンドラー
   */
  dataChange() {
    if (this.infoForm.controls.allCompanyFlag.value === true) {
      this.infoForm.controls.holCompanyName.setValue('');
      this.infoForm.controls.holCompanyCode.setValue('');
    } else {
      this.infoForm.controls.holCompanyName.setValue(this.sessionInfo.loginCompanyName);
      this.infoForm.controls.holCompanyCode.setValue(this.sessionInfo.loginCompanyCode);
    }
  }

  /**
   * 部署選択後処理
   * @param data 選択部署
   */
  selectDataSection(data) {
    const companyCode = data !== null ? data.companyCode : '';
    this.infoForm.controls.holCompanyCode.setValue(companyCode);
    this.infoForm.controls.holStaffName.setValue('');
    this.infoForm.controls.holStaffCode.setValue('');
  }

  /**
   * 部署選択後処理
   * @param data 選択部署
   */
  selectDataSoftware(data) {
    if (data !== null) {
      this.softwareForm.controls.softwareMakerId.setValue(data.value1);
      this.softwareForm.controls.softwareMakerName.setValue(data.value2);
    } else {
      this.softwareForm.controls.softwareMakerId.setValue('');
      this.softwareForm.controls.softwareMakerName.setValue('');
    }
  }

  /**
   * 部署選択後処理
   * @param data 選択部署
   */
  selectDataSoftwareMark(data) {
    this.softwareForm.controls.softwareId.setValue('');
    this.softwareForm.controls.softwareName.setValue('');
  }

  /**
   * リンクボタンクリック用ボタンハンドラー
   */
  linkBtnClickEvent(data) {
    switch (data) {
      case 'licSerialCode':
        this.isLicSerialCode = !this.isLicSerialCode;
        this.licAssetForm.controls.licSerialCode.setValue('');
        break;
      case 'licSerialCodePlural':
        this.isLicSerialCode = !this.isLicSerialCode;
        this.licAssetForm.controls.licSerialCodePlural.setValue('');
        break;
      case 'licProductKey':
        this.isLicProductKey = !this.isLicProductKey;
        this.licAssetForm.controls.licProductKey.setValue('');
        break;
      case 'licProductKeyPlural':
        this.isLicProductKey = !this.isLicProductKey;
        this.licAssetForm.controls.licProductKeyPlural.setValue('');
        break;
      case 'dscAttribute':
        this.isDscAttribute = !this.isDscAttribute;
        this.messageForm.controls.dscAttribute.setValue('');
        break;
      case 'dscAttributePlural':
        this.isDscAttribute = !this.isDscAttribute;
        this.messageForm.controls.dscAttribute.setValue(this.fb.array(this.dscAttributePlural.map((x) => '')));
        break;
    }

  }

  /**
   * データ取得
   */
  getForm() {
    let software = this.softwareForm.getRawValue();
    const info = this.infoForm.getRawValue();
    const licAsset = this.licAssetForm.getRawValue();
    const licLicense = this.licLicenseForm.getRawValue();
    const message = this.messageForm.getRawValue();
    message.dscAttribute1 = message.dscAttributePlural[0];
    message.dscAttribute2 = message.dscAttributePlural[1];
    message.dscAttribute3 = message.dscAttributePlural[2];
    message.dscAttribute4 = message.dscAttributePlural[3];
    message.dscAttribute5 = message.dscAttributePlural[4];
    message.dscAttribute6 = message.dscAttributePlural[5];
    message.dscAttribute7 = message.dscAttributePlural[6];
    message.dscAttribute8 = message.dscAttributePlural[7];
    message.dscAttribute9 = message.dscAttributePlural[8];
    message.dscAttribute10 = message.dscAttributePlural[9];
    message.dscAttribute11 = message.dscAttributePlural[10];
    message.dscAttribute12 = message.dscAttributePlural[11];
    message.dscAttribute13 = message.dscAttributePlural[12];
    message.dscAttribute14 = message.dscAttributePlural[13];
    message.dscAttribute15 = message.dscAttributePlural[14];
    message.dscAttribute16 = message.dscAttributePlural[15];
    message.dscAttribute17 = message.dscAttributePlural[16];
    message.dscAttribute18 = message.dscAttributePlural[17];
    message.dscAttribute19 = message.dscAttributePlural[18];
    message.dscAttribute20 = message.dscAttributePlural[19];
    delete message.dscAttributePlural;
    software = Object.assign({}, software, licAsset, licLicense, info, message);
    return software;
  }
}
