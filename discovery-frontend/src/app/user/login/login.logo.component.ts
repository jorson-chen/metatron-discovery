/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {Component, ElementRef, Injector, OnDestroy, OnInit,} from '@angular/core';
import {AbstractComponent} from "../../common/component/abstract.component";

@Component({
  selector: 'login-logo',
  templateUrl: './login.logo.component.html',
  styles: ['.st0{display:none;}', '.st1{display:inline;fill:#1A274B;}', '.st2{display:inline;}'
    , '.st3{fill:#272950;}', '.st5{fill:#7575EA;}', '.st6{display:inline;fill:#7575EA;}'
    , '.st7{fill:#0E235E;}', '.st8{font-family:\'TitilliumWeb-SemiBold\';}', '.st9{font-size:44.5531px;}']
})
export class LoginLogoComponent extends AbstractComponent implements OnInit, OnDestroy {

  constructor(protected elementRef: ElementRef,
              protected  injector: Injector) {
    super(elementRef, injector);

  }

  /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
   | Override Method
   |-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/

  ngOnInit() {
    super.ngOnInit();
  }

  ngOnDestroy() {
    super.ngOnDestroy();
  }
}