import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import {MatCardModule} from '@angular/material/card';
import {MatMenuModule} from '@angular/material/menu';
import {MatDividerModule} from '@angular/material/divider';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import { FooterComponent } from './pages/footer/footer.component';
import { HeaderComponent } from './pages/header/header.component';
import {MatGridListModule} from '@angular/material/grid-list';
import { ProjectsComponent } from './pages/projects/projects.component';
import { PickerModule } from '@ctrl/ngx-emoji-mart';
import {MatButtonModule} from '@angular/material/button';
import { ContactComponent } from './pages/contact/contact.component';
import { AgmCoreModule } from '@agm/core';
import {MatFormFieldModule} from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ResumeComponent } from './pages/resume/resume.component';
import { ExamplePdfViewerComponent } from './example-pdf-viewer/example-pdf-viewer.component';
import { NgxExtendedPdfViewerModule } from 'ngx-extended-pdf-viewer';
import { HttpClientModule } from '@angular/common/http';





@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FooterComponent,
    HeaderComponent,
    ProjectsComponent,
    ContactComponent,
    ResumeComponent,
    ExamplePdfViewerComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    NgxExtendedPdfViewerModule,
    ReactiveFormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSlideToggleModule,
    MatCardModule,
    MatMenuModule,
    MatDividerModule,
    MatProgressBarModule,
    MatGridListModule,
    PickerModule,
    MatButtonModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyAFgM81Qz-SwfTzUsr4F51AgDj0HdN88CQ'
    }),
    MatFormFieldModule,
    NgxExtendedPdfViewerModule,
    

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
