import { Component } from '@angular/core';
import { pdfDefaultOptions, NgxExtendedPdfViewerService } from 'ngx-extended-pdf-viewer';


@Component({
  selector: 'app-resume',
  templateUrl: './resume.component.html',
  styleUrls: ['./resume.component.css']
})
export class ResumeComponent {

  public page = 5;

  constructor(){
    pdfDefaultOptions.assetsFolder = 'bleeding-edge';
  }

  public pageLabel: any;

}
