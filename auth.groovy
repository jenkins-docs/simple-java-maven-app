def auth(token) {
    def (usr, pass) = token.split(':')
    if (usr == 'banana') {
        println "banana"
    }
    if (pass == 'apple') {
        println "apple"
    }
}

return [
    auth: this.&auth
]