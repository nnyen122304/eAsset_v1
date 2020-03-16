/**
 * 非オブジェクト型のRESTレスポンス
 */
export class NonObjectResponse<T> {
  /**
   * RESTレスポンス値
   */
  value?: T;
}
