package com.gitzblitz.mychatapp.models

/**
 * Created by george.ngethe on 01/03/2018.
 */
class ChatMessage() {
  var id: String? = null
  var text:String? = null
  var name: String? = null

  constructor(id:String, text:String, name: String): this(){
    this.id = id
    this.text = text
    this.name = name
  }
}