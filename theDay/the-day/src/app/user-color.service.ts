import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserColorService {

  constructor(
    private http: HttpClient){}

    getUserColor(){
      return this.http.get<any>('http://localhost:5000/getUsers');
    }

    changeColorForUser(user,color){
      return this.http.put<any>('http://localhost:5000/changeUserColor?user='+user+"&color="+color,{});
    }

    addNewUser(user){
      return this.http.post<any>('http://localhost:5000/addUser', {user:user,color:"undefined"});
    }

    removeUser(user){
      return this.http.delete<any>('http://localhost:5000/removeUser?user='+user,{});
    }
}
