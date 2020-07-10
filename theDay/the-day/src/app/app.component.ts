import { Component } from '@angular/core';
import { UserColorService} from '../app/user-color.service'
import {Color} from '../app/color'
import {ElementRef,ViewChild} from '@angular/core'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {

  @ViewChild('theday') theday: ElementRef;
  title = 'Favourite Colors';
  thedaycardshow: boolean = false;
  colors: Color[];
  userColor = {};
  users: string[];
  selectedUser: string;
  displayDialog : boolean = false;
  newUser :string;
  displayGiftDialog : boolean = false;
  giftNumber: number;

  constructor(private elementRef: ElementRef,private uc: UserColorService) {
    this.colors = [{name:"undefined"},{name:"Blue"},{name:"White"},{name:"Purple"},{name:"Green"}];
   }

  ngOnInit(){
    this.uc.getUserColor().subscribe((uc) => 
      {
        this.users = Object.keys(uc);
        this.users.forEach(user => {this.userColor[user] = new Color(uc[user]); } );
      }
    );    
  }

  changedColor(user, event){
    this.uc.changeColorForUser(user,event.value.name).subscribe();
  }

  showDialog(){
    this.displayDialog = true;
  }

  userExists(user){
    if(user == "" || user === undefined){
      return false;
    }
    return this.users.includes(user);
  }

  onSubmit(){
    this.uc.addNewUser(this.newUser).subscribe( res =>
      this.ngOnInit()
    );
  }

  handleClickRemove(user){
    if(user == this.selectedUser){
      this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor = "white"    
      this.theday.nativeElement.innerHTML = ""
      this.selectedUser = undefined
    }
    this.uc.removeUser(user).subscribe(res=>
      this.ngOnInit()
    );
  }

  userIsSelected(){
    return !(this.selectedUser === undefined)
  }

  handleClickCelebrate(user){
    if(this.userColor[user].name != "undefined"){
      this.selectedUser = user;
      this.thedaycardshow = true;
      this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor = this.userColor[user].name.toLowerCase();    
      this.theday.nativeElement.innerHTML = "It is " + user + "'s day!!"
    }
  }

  openTheGiftHandle(event){
    this.displayGiftDialog = true;
    this.giftNumber = Math.pow(event,2);
  }

}
