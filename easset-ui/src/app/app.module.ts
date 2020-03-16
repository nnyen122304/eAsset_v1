import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// free-lib
import { SnackbarModule } from 'ngx-snackbar';
import { NgxSpinnerModule } from 'ngx-spinner';
import { NgIdleModule } from '@ng-idle/core';

// app
import { LoadingInterceptor } from './interceptors/loading-interceptor';
import { RequestMappingInterceptor } from './interceptors/request-mapping-interceptor';
import { ResponseMappingInterceptor } from './interceptors/response-mapping-interceptor';
import { ErrorInterceptor } from './interceptors/error-interceptor';
import { MessageService } from './services/message.service';
import { HeaderComponent } from './parts/header/header.component';
import { SharedModule } from './shared.module';
import { SessionService } from './services/session.service';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
  ],
  imports: [
    BrowserModule,
    SharedModule,
    AppRoutingModule,
    SnackbarModule.forRoot(),
    NgxSpinnerModule,
    NgIdleModule.forRoot()
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: RequestMappingInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ResponseMappingInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true},
    MessageService, SessionService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
