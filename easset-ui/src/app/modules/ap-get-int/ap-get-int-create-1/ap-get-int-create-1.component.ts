import { Component, OnInit, ViewChild, ViewContainerRef, ComponentFactoryResolver } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApGetIntComponent } from '../ap-get-int.component';
import {  FormBuilder } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';
import { SessionInfo } from 'src/app/models/session-info';

@Component({
  selector: 'app-ap-get-int-create-1',
  templateUrl: './ap-get-int-create-1.component.html',
  styleUrls: ['./ap-get-int-create-1.component.scss']
})
export class ApGetIntCreate1Component extends AbstractChildComponent<ApGetIntComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * accessMenu
   */
  accessMenu = '';

  /**
   * accessLevel
   */
  accessLevel = '';

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private componentFactoryResolver: ComponentFactoryResolver,
    private messageService: MessageService,
    private masterService: MasterService
  ) {
    super();
  }

  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.setAccessLevel();
    this.setAccessMenu();
  }


  /**
   * Get access level
   */
  setAccessLevel() {
    switch (this.sessionInfo.currentAccessLevel) {
      case 'S':
        this.accessLevel = 'admin';
        break;
      case 'C':
        this.accessLevel = 'general';
        break;
      case 'B':
        this.accessLevel = 'asset_manager';
        break;
      default:
        this.accessLevel = 'category';
        break;
    }
  }

  /**
   * Get access menu
   */
  setAccessMenu() {
    switch (this.sessionInfo.currentMenuId) {
      case '08010':
        this.accessMenu = 'menuIdApGetIntCreate1';
        break;
      case '08020':
        this.accessMenu = 'menuIdApGetIntCreate2';
        break;
      case '08030':
        this.accessMenu = 'menuIdApGetIntCreate3';
        break;
        case '08098':
          this.accessMenu = 'menuIdApGetIntEapp';
          break;
        case '08099':
          this.accessMenu = 'menuIdApGetIntRef';
          break;
      default:
        this.accessMenu = 'menuIdApGetIntCreate1';
        break;
    }
  }

}
