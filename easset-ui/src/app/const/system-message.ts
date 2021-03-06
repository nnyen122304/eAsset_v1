/**
 * システム共通メッセージ
 * パラメータ付きのメッセージはsprintfの形式で定義（https://www.npmjs.com/package/sprintf-js）
 */
// tslint:disable-next-line: no-namespace
export namespace SystemMessage {
  /**
   * 情報メッセージ
   */
  export namespace Info {
    /**
     * ファイルをアップロードしました。\n■サーバー上保存ファイル\n　%s\n■選択したファイル\n　ファイル名：%s\n　サイズ-%d(byte)\n　更新日-%s
     * ※　リファレンス実装用パラメータ付きメッセージ
     */
    export const msg100000 = 'ファイルをアップロードしました。\n■サーバー上保存ファイル名\n　%s\n■選択したファイル\n　ファイル名：%s\n　サイズ：%d(byte)\n　更新日：%s';

    /**
     * 保存しました。
     */
    export const msg100001 = '保存しました。';

    /**
     * 棚卸データ作成処理を実行しました。
     */
    export const msg100002 = '棚卸データ作成処理を実行しました。';

    /**
     * 更新しました。
     */
    export const msg100003 = '更新しました。';

    /**
     * 棚卸データ公開処理を実行しました。
     */
    export const msg100004 = '棚卸データ公開処理を実行しました。';

    /**
     * 処理中断リクエストを行いました。
     */
    export const msg100005 = '処理中断リクエストを行いました。';

    /**
     * 'リース・レンタル"を取り込む場合、固定資産も同時に取り込む必要があります。
     */
    export const msg100006 = 'リース・レンタル"を取り込む場合、<br>固定資産も同時に取り込む必要があります。<br><br>※　ファイナンスリースの帳簿情報が"固定資産"として保持されているため';

    /**
     * 取込処理を実行しました。
     */
    export const msg100007 = '取込処理を実行しました。';

    /**
     * データが選択されていません。
     */
    export const msg100008 = 'データが選択されていません。';

    /**
     * 権限を追加しました。
     */
    export const msg100009 = '権限を追加しました。';

    /**
     * 権限を削除しました。
     */
    export const msg100010 = '権限を削除しました。';

    /**
     * 権限削除処理は即座に反映されます。削除してもよろしいですか？
     */
    export const msg100011 = '権限削除処理は即座に反映されます。削除してもよろしいですか？';

    /**
     * 情報を追加しました。
     */
    export const msg100012 = '情報を追加しました。';

    /**
     * データが選択されていません。
     */
    export const msg100013 = 'データが選択されていません。';

    /**
     * 督促メールを送信しました。
     */
    export const msg100014 = '督促メールを送信しました。';

    /**
     * 督促メールを送信しました。
     */
    export const msg100015 = '棚卸完了報告しました。';

    /**
     * ファイルをアップロードしました。一括更新ボタンをクリックしてください。
     */
    export const msg100016 = 'ファイルをアップロードしました。一括更新ボタンをクリックしてください。';

    /**
     * 処理が完了しました
     */
    export const msg100017 = '処理が完了しました。';

    /**
     * 処理が完了しました
     */
    export const msg100018 = '複数の部署が選択されています。変更しますか？';

    /**
     * 精査担当部署が未設定の行が選択されています。
     */
    export const msg100019 = '精査担当部署をクリアしますか？';

    /**
     * 編集中の精査担当部署がありますが終了してもよろしいでしょうか？
     */
    export const msg100020 = '編集中の精査担当部署がありますが終了してもよろしいでしょうか？';

    /**
     *  申請書を削除します。よろしいでしょうか。
     */
    export const msg100021 = '申請書を削除します。\nよろしいでしょうか。';

    /**
     *  削除しました。
     */
    export const msg100022 = '削除しました。';

    /**
     * 情報機器等が作成されました。
     */
    export const msg100023 = '情報機器等が作成されました。';

    /**
     * 作業依頼を行いました。
     */
    export const msg100024 = '作業依頼を行いました。';

  }
  /**
   * 警告メッセージ
   */
  export namespace Warn {
    /**
     *
     */
    export const msg200001 = '';

    /**
     * データが存在しません
     */
    export const msg200002 = '対象データが存在しません。';

    /**
     * 一度公開した事のある資産区分について再作成すると、棚卸実施状況はクリアされます。
     */
    export const msg200003 = '一度公開した事のある資産区分について再作成すると、棚卸実施状況はクリアされます。';

    /**
     * 保存前の状態でエクスポートされます。
     */
    export const msg200004 = '保存前の状態でエクスポートされます。';

    /**
     * 棚卸実施項目の「有」「無」を指定した行のみ反映しました。
     */
    export const msg200005 = '棚卸実施項目の「有」「無」を指定した行のみ反映しました。';

    /**
     * 棚卸実施項目の「有」「無」を指定した行のみ反映しました。
     */
    export const msg200006 = '棚卸実施項目の「有」「無」を指定した行のみ反映しました。';

    /**
     * 再作成を実行すると精査実施状況はクリアされます。
     */
    export const msg200007 = '再作成を実行すると精査実施状況はクリアされます。';

    /**
     * 社内システム（基幹システム）関連資産取得の場合のみCIO承認が必要となります。
     */
    export const msg200008 = '社内システム（基幹システム）関連資産取得の場合のみCIO承認が必要となります。';

    /**
     * データ
     */
    export const msg200037 = 'データ';

    /**
     * の入力を「OK」ボタンをクリックして確定させるか、キャンセルしてください。
     */
    export const msg200009 = 'の入力を「OK」ボタンをクリックして確定させるか、キャンセルしてください。';
  }
  /**
   * エラーメッセージ
   */
  export namespace Err {
    /**
     * データの取得に失敗しました。
     */
    export const msg300001 = 'データの取得に失敗しました。';

    /**
     * 処理対象が選択されていません。
     */
    export const msg300002 = '処理対象が選択されていません。';

    /**
     * ステータスを選択してください。
     */
    export const msg300004 = 'ステータスを選択してください。';

    /**
     * 取込対象を選択してください。
     */
    export const msg300005 = '取込対象を選択してください。';

    /**
     * 棚卸データ作成が実行されていないため、処理を継続できません。
     */
    export const msg300006 = '棚卸データ作成が実行されていないため、処理を継続できません。';

    /**
     * 管轄部署以外の保有部署が選択されています。<br>管轄部署の保有部署のみ棚卸完了報告ができます。
     */
    export const msg300007 = '管轄部署以外の保有部署が選択されています。<br>管轄部署の保有部署のみ棚卸完了報告ができます。';

    /**
     * 保有部署[SECTION]は棚卸未実施のデータが存在します。<br>棚卸を実施してください。
     */
    export const msg300008 = '保有部署%sは棚卸未実施のデータが存在します。<br>棚卸を実施してください。';

    /**
     * 保有部署[SECTION]は実査表印刷対象外です。
     */
    export const msg300009 = '保有部署%sは実査表印刷対象外です。';

    /**
     * 保有部署[SECTION]は督促メール送信対象外です。
     */
    export const msg300010 = '保有部署%sは督促メール送信対象外です。';

    /**
     * 資産区分が異なります。同一の資産区分を選択してください。
     */
    export const msg300011 = '資産区分が異なります。同一の資産区分を選択してください。';

    /**
     * 選択した保有部署に「(保有部署不明)」もしくは、「(資産情報不明)」が混在して選択されています。<br>「(保有部署不明)」もしくは、「(資産情報不明)」が混在したまま明細ダウンロードすることはできません。
     */
    export const msg300012 = '選択した保有部署に「(保有部署不明)」もしくは、「(資産情報不明)」が混在して選択されています。<br>「(保有部署不明)」もしくは、「(資産情報不明)」が混在したまま明細ダウンロードすることはできません。';

    /**
     * 保有部署[SECTION]は実査表印刷対対象外です。
     */
    export const msg300013 = '保有部署%sは実査表印刷対対象外です。';

    /**
     * 資産区分[ASSET]は実査表印刷対象外です。
     */
    export const msg300014 = '資産区分%sは実査表印刷対象外です。';

    /**
     * 一括入力CSVファイルが更新されたため、再度選択してください。
     */
    export const msg300015 = '一括入力CSVファイルが更新されたため、再度選択してください。';

    /**
     * 一括入力CSVファイルが削除されたため、再度選択してください。
     */
    export const msg300016 = '一括入力CSVファイルが削除されたため、再度選択してください。';

    /**
     * 会社は必須入力です。
     */
    export const msg300017 = '会社は必須入力です。';

    /**
     * コードは必須入力です。
     */
    export const msg300018 = 'コードは必須入力です。';

    /**
     * ソート値は必須入力です。
     */
    export const msg300019 = 'ソート値は必須入力です。';

    /**
     * コードが重複しています。
     */
    export const msg300020 = 'コードが重複しています。';

    /**
     * 既に精査完了処理済の行（「精査完了：〇」）が選択されているため、処理を中断しました。\n選択できるのは「精査完了：－」の行だけです。
     */
    export const msg300021 = '既に精査完了処理済の行（「精査完了：〇」）が選択されているため、処理を中断しました。\n選択できるのは「精査完了：－」の行だけです。';

    /**
     * 精査未実施、もしくは実施中の行が選択されているため、処理を中断しました。\n選択できるのは「精査状況(資産件数)　＞　未：0」の行だけです。
     */
    export const msg300022 = '精査未実施、もしくは実施中の行が選択されているため、処理を中断しました。\n選択できるのは「精査状況(資産件数)　＞　未：0」の行だけです。';

    /**
     * 精査完了処理が実行されていない行（「精査完了：－」）が選択されているため、処理を中断しました。\n選択できるのは「精査完了：〇」の行だけです。
     */
    export const msg300023 = '精査完了処理が実行されていない行（「精査完了：－」）が選択されているため、処理を中断しました。\n選択できるのは「精査完了：〇」の行だけです。';

    /**
     * 処理対象
     */
    export const msg300024 = '処理対象';

    /**
     * ダウンロード
     */
    export const msg300025 = '経費負担精査データが作成されていません。';

    /**
     * 精査種別が異なる行の明細ダウンロードは同時に実行できません。\n精査種別が同じ行のみ選択して、再度実行してください。
     */
    export const msg300026 = '精査種別が異なる行の明細ダウンロードは同時に実行できません。\n精査種別が同じ行のみ選択して、再度実行してください。';

    /**
     * 依頼メールを送信する精査担当部署をチェックしてください。
     */
    export const msg300027 = '依頼メールを送信する精査担当部署をチェックしてください。';

    /**
     * 変更する精査担当部署をチェックしてください。
     */
    export const msg30028 = '変更する精査担当部署をチェックしてください。';

    /**
     * 精査担当部署が未設定の行が選択されています。
     */
    export const msg30029 = '精査担当部署が未設定の行が選択されています。';

    /**
     * 検索条件を入力してください。
     */
    export const msg30030 = '検索条件を入力してください。';

    /**
     * 最大文字数
     * ERR_MSG_ITEM_MAX_SIZE
     */
    export const msg30031 = (n1: string = '', n2: string = '') => `${n1} : 入力可能な文字数は半角で ${n2} 文字までです。`;

    /**
     * 最小値
     * ERR_MSG_ITEM_MIN_VALUE
     */
    export const msg30032 = (n1: string, n2: string = '') => `${n1} : ${n2} 以上の値を入力してください。`;

    /**
     * 最大値
     * ERR_MSG_ITEM_MAX_VALUE
     */
    export const msg30033 = (n1: string, n2: string = '') => `${n1} : ${n2} 以下の値を入力してください。`;

    /**
     * 最大値
     * ERR_MSG_ITEM_RESTRICT
     */
    export const msg30034 = (n: string) => `${n} : 不正な値が入力されました。`;

    /**
     * 最大値
     * ERR_MSG_ITEM_RESTRICT_DISP_VAL
     */
    export const msg30035 = (n1: string, n2: string = '') => `${n1}  : 不正な値が入力されました。(${n2})`;

    /**
     * 必須
     * ERR_MSG_ITEM_REQUIRED
     */
    export const msg30036 = (n: string) => `${n} : 必須入力です。`;

    /**
     * 条件付必須
     * ERR_MSG_ITEM_REQUIRED_CONDITIONAL
     */
    export const msg30037 = (n1: string, n2: string = '') => `${n1} : ${n2}必須入力です。`;

    /**
     * 条件付必須
     */
    export const msg30038 = (n1: string) => `${n1} が選択されていません。`;
  }
  /**
   * 確認メッセージ
   */
  export namespace Conf {

    /**
     * 値が変更されてます。変更内容を保存せずに処理を続行しますか？
     */
    export const msg400001 = '値が変更されてます。<br>変更内容を保存せずに処理を続行しますか？';

    /**
     * 所属部署の部署長に棚卸完了報告されます。よろしいでしょうか？
     */
    export const msg400002 = '所属部署の部署長に棚卸完了報告されます。<br>よろしいでしょうか？';

    /**
     * 処理を中断してもよろしいですか？
     */
    export const msg400003 = '処理を中断してもよろしいですか？';

  }
}
