import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-celebration',
  templateUrl: './celebration.component.html',
  styleUrls: ['./celebration.component.css']
})
export class CelebrationComponent implements OnInit {

  @Input() user;
  @Input() show : boolean;
  @Output() openTheGift: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  makeSeed(): number {
    var text = "";
    var possible = "123456789";
  
    for (var i = 0; i < 4; i++)
      text += possible.charAt(Math.floor(Math.random() * possible.length));
  
    return parseInt(text);
  }

  openGift(){   
    this.openTheGift.emit(this.makeSeed());
  }

}
