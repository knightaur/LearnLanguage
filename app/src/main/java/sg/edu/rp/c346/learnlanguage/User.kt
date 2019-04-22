package sg.edu.rp.c346.learnlanguage

class User{
    var email:String
    var pass:String
    var role:String
    var username:String
    var skype:String
    var wechat:String
    var line:String
    var request:ArrayList<String>

    constructor(email:String, pass:String, role:String){
        this.email = email
        this.pass = pass
        this.role = role
        this.username = ""
        this.skype = ""
        this.wechat = ""
        this.line = ""
        this.request = ArrayList()
    }

    constructor(email:String, pass:String, role:String, username:String, skype:String, wechat:String, line:String){
        this.email = email
        this.pass = pass
        this.role = role
        this.username = username
        this.skype = skype
        this.wechat = wechat
        this.line = line
        this.request = ArrayList()
    }



}