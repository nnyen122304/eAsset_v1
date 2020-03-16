import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';
import * as wjcCore from 'wijmo/wijmo';

// tslint:disable-next-line: max-line-length
const key = '453633419999943#B0IOMI7ckJye0ICbuFkI1pjIEJCLi4TP7lFdLNUZtFlbzREWBV4d4UUSYNmcw2UU7YWY8VlRXBjTxJWa5okTutiYydVaLVnbRtEMRpHWJZnQLN6djF4URZGZFJzYy4UOpF5dFZmRBVnUV3WVWNWOvMFZwEHZFFXNmRlTrJ4brd6LPd7KUlmURNTdmVGaMljZVJGSRF6cSBFZFNXejlEOwBVZqJnZwRmRCdTTstyL65WW88WbhNnZiJlb5N4Y9kUZrx4LKt4dxc7VWRzRRJnRLp4MYBja8w6Smd7bGZDN9tke9VGMy2SaTpHVJZXVV3GRyQWR9gGZmNVVqBjMTlXYXFzaiZzZWpHRQJjZEJkQ926R4hGN5EGSsJWVz3CSaNzV0NjRHt6btpUW7Z6aZBlMPpnYMpnMEdHR9sUZC9GWklzT4s4KYR4UqNEN5UVN6hTdoxkVGJHUURkcUFXMI36c73WW42mes3GZXJUN8oHUiojITJCLicTO7IkMDdTMiojIIJCL8ITMyYTN6EzM0IicfJye35XX3JSSwIjUiojIDJCLi86bpNnblRHeFBCI4VWZoNFelxmRg2Wbql6ViojIOJyes4nI5kkTRJiOiMkIsIibvl6cuVGd8VEIgIXZ7VWaWRncvBXZSBybtpWaXJiOi8kI1xSfis4N8gkI0IyQiwiIu3Waz9WZ4hXRgAydvJVa4xWdNBybtpWaXJiOi8kI1xSfiQjR6QkI0IyQiwiIu3Waz9WZ4hXRgACUBx4TgAybtpWaXJiOi8kI1xSfiMzQwIkI0IyQiwiIlJ7bDBybtpWaXJiOi8kI1xSfiUFO7EkI0IyQiwiIu3Waz9WZ4hXRgACdyFGaDxWYpNmbh9WaGBybtpWaXJiOi8kI1tlOiQmcQJCLiIzMzQjMwASOxETM9EDMyIiOiQncDJCLiEjLw8CMucjMxIiOiMXbEJCLi8LpnrJvk/IvlrKomPqgjb8gjfrgjf9gjz1gjz0gjDrgjLiOiEmTDJCLiMDN9kTO9kTM4MzM6MTN4IiOiQWSiwSfiMjd9EDMyIiOiIXZ6JCLlNHbhZ';
wjcCore.setLicenseKey(key);

if (environment.production) {
  enableProdMode();
  if (window) { // 開発時以外はconsole出力無し
    window.console.log = () => {};
  }
}

if (!Object.entries) {
  Object.entries = obj => {
    const ownProps = Object.keys( obj );
    let i = ownProps.length;
    const resArray = new Array(i); // preallocate the Array
    while (i--) {
      resArray[i] = [ownProps[i], obj[ownProps[i]]];
    }

    return resArray;
  };
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
