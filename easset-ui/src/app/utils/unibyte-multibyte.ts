/**
 * 全角へ変換
 * @param str 文字配列
 */

export function toMultibyte(str: string) {
  if (!str) {
    return str;
  }
  return str.replace(/[A-Za-z0-9!-~]/g, (s: string) => {
    return String.fromCharCode(s.charCodeAt(0) + 0xFEE0);
});
}
