import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WjGridModule } from 'wijmo/wijmo.angular2.grid';
import { WjNavModule } from 'wijmo/wijmo.angular2.nav';
import { WjInputModule } from 'wijmo/wijmo.angular2.input';
import 'wijmo/cultures/wijmo.culture.ja.js';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DatePipe } from '@angular/common';
import { EaFlexGrid } from './components/ea-flex-grid/ea-flex-grid.component';
import { EaFlexGridColumn } from './components/ea-flex-grid/ea-flex-column.component';
import { EaViewStackTab } from './components/view-stack/ea-view-stack-tab/ea-view-stack-tab.component';
import { EaInputMask } from './components/ea-input-mask/ea-input-mask.component';
import { EaInputNumber } from './components/ea-input-number/ea-input-number.component';
import { EaPopup } from './components/ea-popup/ea-popup.component';
import { SectionSelectionComponent } from './parts/lov-input/section-selection/section-selection.component';
import { PrivilegeSelectionComponent } from './parts/lov/privilege-selection/privilege-selection.component';
import { StaffSelectionComponent } from './parts/lov-input/staff-selection/staff-selection.component';
import { MessagePopupComponent } from './parts/message-popup/message-popup.component';
import { ConfirmPopupComponent } from './parts/confirm-popup/confirm-popup.component';
import { CostSectionSelectionComponent } from './parts/lov-input/cost-section-selection/cost-section-selection.component';
import { ReoAstComponent } from './parts/lov-input/reo-ast/reo-ast.component';
import { AssetCategoryComponent } from './parts/lov-input/asset-category/asset-category.component';
import { StatusSelectionComponent } from './parts/lov/status-selection/status-selection.component';
import { ApSectionSelectionComponent } from './parts/lov-input/ap-section-selection/ap-section-selection.component';
import { ProjectSelectionComponent } from './parts/lov-input/project-selection/project-selection.component';
import { CostScrInputSectionComponent } from './parts/lov/cost-scr-input-section/cost-scr-input-section.component';
import { PpfsGroupSelectionComponent } from './parts/lov-input/ppfs-group-selection/ppfs-group-selection.component';
import { HolStaffSelectionComponent } from './parts/lov-input/hol-staff-selection/hol-staff-selection.component';
import { SoftwareMakerSelectionComponent } from './parts/lov-input/software-maker-selection/software-maker-selection.component';
import { CodeNameSelectionComponent } from './parts/lov-input/code-name-selection/code-name-selection.component';
import { DownloadOptionComponent } from './parts/option/download-option/download-option.component';
import { PrintOptionComponent } from './parts/option/print-option/print-option.component';
import { DownloadOptionFldIntComponent } from './parts/option/download-option-fld-int/download-option-fld-int.component';
import { DownloadOptionPropComponent } from './parts/option/download-option-prop/download-option-prop.component';
import { LicenseTypeComponent } from './parts/lov-input/license-type/license-type.component';
import { ApGetIntAmountRangeSelectionComponent } from './parts/lov-input/ap-get-int-amount-range-selection/ap-get-int-amount-range-selection.component';
import { PpfsSetchiSelectionComponent } from './parts/lov-input/ppfs-setchi-selection/ppfs-setchi-selection.component';
import { SoftwareSelectionComponent } from './parts/lov-input/software-selection/software-selection.component';
import { FileUploadSelectionComponent } from './parts/lov-input/file-upload-selection/file-upload-selection.component';
import { NamePurCompanySelectionComponent } from './parts/lov-input/name-pur-company-selection/name-pur-company-selection.component';
import { ApGetIntSearchConditionComponent } from './parts/screen/ap-get-int/ap-get-int-search-condition/ap-get-int-search-condition.component';
import { ApGetTanSearchConditionComponent } from './parts/screen/ap-get-tan/ap-get-tan-search-condition/ap-get-tan-search-condition.component';
import { HistoryComponent } from './parts/screen/history/history.component';
import { LicenseSearchConditionComponent } from './parts/screen/license/license-search-condition/license-search-condition.component';
import { ComLicenseSearchConditionComponent } from './parts/screen/license/com-license-search-condition/com-license-search-condition.component';
import { ApLicenseSearchConditionComponent } from './parts/screen/license/ap-license-search-condition/ap-license-search-condition.component';
import { ApGetIntResultListComponent } from './parts/screen/ap-get-int/ap-get-int-result-list/ap-get-int-result-list.component';
import { AssetSearchConditionComponent } from './parts/screen/asset/asset-search-condition/asset-search-condition.component';
import { ComAssetSearchConditionComponent } from './parts/screen/asset/com-asset-search-condition/com-asset-search-condition.component';
import { AssetResultListComponent } from './parts/screen/asset/asset-result-list/asset-result-list.component';
import { AssetEntryBodyComponent } from './parts/screen/asset/asset-entry-body/asset-entry-body.component';
import { ApGetTanResultListComponent } from './parts/screen/ap-get-tan/ap-get-tan-result-list/ap-get-tan-result-list.component';
import { CurrentYearSelectionComponent } from './parts/lov-input/current-year-selection/current-year-selection.component';
import { AstSelectionComponent } from './parts/lov-input/ast-selection/ast-selection.component';
import { ApAssetResultListComponent } from './parts/screen/ap-asset-result-list/ap-asset-result-list.component';
import { ApGetIntAstCategoryComponent } from './parts/lov-input/ap-get-int-ast-category/ap-get-int-ast-category.component';
import { LicenseResultListComponent } from './parts/screen/license/license-result-list/license-result-list.component';
import { AssetLineOsComponent } from './parts/screen/asset/asset-entry-body/asset-line-os/asset-line-os.component';
import { AssetLineNetworkComponent } from './parts/screen/asset/asset-entry-body/asset-line-network/asset-line-network.component';
import { ApLicenseResultListComponent } from './parts/screen/license/ap-license-result-list/ap-license-result-list.component';




@NgModule({
  declarations: [
    EaFlexGrid,
    EaFlexGridColumn,
    EaViewStackTab,
    EaInputMask,
    EaInputNumber,
    EaPopup,
    SectionSelectionComponent,
    PrivilegeSelectionComponent,
    StaffSelectionComponent,
    MessagePopupComponent,
    ConfirmPopupComponent,
    CostSectionSelectionComponent,
    DownloadOptionComponent,
    PrintOptionComponent,
    DownloadOptionFldIntComponent,
    ReoAstComponent,
    AssetCategoryComponent,
    DownloadOptionPropComponent,
    StatusSelectionComponent,
    ApSectionSelectionComponent,
    ProjectSelectionComponent,
    CostScrInputSectionComponent,
    PpfsGroupSelectionComponent,
    HistoryComponent,
    HolStaffSelectionComponent,
    SoftwareMakerSelectionComponent,
    AssetSearchConditionComponent,
    ComAssetSearchConditionComponent,
    CodeNameSelectionComponent,
    LicenseTypeComponent,
    ApGetIntAmountRangeSelectionComponent,
    PpfsSetchiSelectionComponent,
    SoftwareSelectionComponent,
    FileUploadSelectionComponent,
    NamePurCompanySelectionComponent,
    LicenseSearchConditionComponent,
    ComLicenseSearchConditionComponent,
    ApLicenseSearchConditionComponent,
    ApGetIntSearchConditionComponent,
    ApGetTanSearchConditionComponent,
    ApGetTanResultListComponent,
    CurrentYearSelectionComponent,
    HistoryComponent,
    ApGetIntResultListComponent,
    AssetResultListComponent,
    AssetEntryBodyComponent,
    AstSelectionComponent,
    ApAssetResultListComponent,
    ApGetIntAstCategoryComponent,
    LicenseResultListComponent,
    ApGetIntAstCategoryComponent,
    AssetLineOsComponent,
    AssetLineNetworkComponent,
    ApLicenseResultListComponent

  ],
  imports: [
    CommonModule,
    WjGridModule,
    WjInputModule,
    WjNavModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  exports: [
    WjGridModule,
    WjInputModule,
    WjNavModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    EaFlexGrid,
    EaFlexGridColumn,
    EaViewStackTab,
    EaInputMask,
    EaInputNumber,
    EaPopup,
    SectionSelectionComponent,
    PrivilegeSelectionComponent,
    StaffSelectionComponent,
    MessagePopupComponent,
    ConfirmPopupComponent,
    CostSectionSelectionComponent,
    DownloadOptionComponent,
    PrintOptionComponent,
    DownloadOptionFldIntComponent,
    ReoAstComponent,
    AssetCategoryComponent,
    DownloadOptionPropComponent,
    StatusSelectionComponent,
    ApSectionSelectionComponent,
    ProjectSelectionComponent,
    CostScrInputSectionComponent,
    PpfsGroupSelectionComponent,
    HistoryComponent,
    HolStaffSelectionComponent,
    SoftwareMakerSelectionComponent,
    AssetSearchConditionComponent,
    ComAssetSearchConditionComponent,
    CodeNameSelectionComponent,
    LicenseTypeComponent,
    ApGetIntAmountRangeSelectionComponent,
    PpfsSetchiSelectionComponent,
    SoftwareSelectionComponent,
    FileUploadSelectionComponent,
    NamePurCompanySelectionComponent,
    LicenseSearchConditionComponent,
    ComLicenseSearchConditionComponent,
    ApLicenseSearchConditionComponent,
    ApGetIntSearchConditionComponent,
    ApGetTanSearchConditionComponent,
    ApGetTanResultListComponent,
    CurrentYearSelectionComponent,
    HistoryComponent,
    ApGetIntResultListComponent,
    AssetResultListComponent,
    AssetEntryBodyComponent,
    AstSelectionComponent,
    ApAssetResultListComponent,
    ApGetIntAstCategoryComponent,
    LicenseResultListComponent,
    ApGetIntAstCategoryComponent,
    ApLicenseResultListComponent

  ],
  providers: [DatePipe],
})
export class SharedModule {
}
