import { Component, OnInit } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApGetTanComponent } from '../ap-get-tan.component';

@Component({
  selector: 'app-ap-get-tan-entry',
  templateUrl: './ap-get-tan-entry.component.html',
  styleUrls: ['./ap-get-tan-entry.component.scss']
})
export class ApGetTanEntryComponent extends AbstractChildComponent<ApGetTanComponent> implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

}
