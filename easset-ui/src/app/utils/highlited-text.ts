/**
 * ハイライト中テキスト取得
 */
export function GetHighlightedText() {
  let text = '';
  if (window.getSelection) {
      text = window.getSelection().toString();
  // tslint:disable-next-line: no-any
  } else if ((document as any).selection && (document as any).selection.type !== 'Control') {
      // tslint:disable-next-line: no-any
      text = (document as any).createRange().text;
  }
  return text;
}

/**
 * ハイライト中テキスト削除
 * @param element 該当項目
 */
export function DeleteHighlightedText(element) {
    let text = element.value;
    text = text.slice(0, element.selectionStart) + text.slice(element.selectionEnd);
    element.value = text;
}
