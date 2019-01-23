def auth(token) {
    def (usr, pass) = token.split(':')
    assert usr == 'banana'
    assert pass == 'apple'
}

return [
    auth: this.&auth
]