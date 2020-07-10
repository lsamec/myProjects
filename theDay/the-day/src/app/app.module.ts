import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {CardModule} from 'primeng/card';
import {ButtonModule} from 'primeng/button';
import { AppComponent } from './app.component';
import { HttpClientModule} from '@angular/common/http';
import {DropdownModule} from 'primeng/dropdown'; 
import { FormsModule }   from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {DialogModule} from 'primeng/dialog';
import { CelebrationComponent } from './celebration/celebration.component';

@NgModule({
  declarations: [
    AppComponent,
    CelebrationComponent
  ],
  imports: [
    BrowserModule,
    CardModule,
    ButtonModule,
    DropdownModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    DialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
