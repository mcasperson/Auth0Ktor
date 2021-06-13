package com.mathewceron

import java.io.InputStream
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.security.interfaces.RSAPublicKey


fun readPublicKey(key: InputStream?): RSAPublicKey? {
    if (key == null) return null
    val certFactory: CertificateFactory = CertificateFactory.getInstance("X.509")
    val cer: X509Certificate = certFactory.generateCertificate(key) as X509Certificate
    return cer.publicKey as RSAPublicKey
}
