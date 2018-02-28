package com.gitzblitz.mychatapp.models

/**
 * Created by george.ngethe on 28/02/2018.
 */
class Users() {
  var display_name: String? = null
  var image: String? = null
  var thumb_image: String? = null
  var status: String? = null

  constructor(display_name: String, image: String, thumb_image: String, status: String):this(){

    this.display_name = display_name
    this.image = image
    this.status = status
    this.thumb_image = thumb_image

  }
}