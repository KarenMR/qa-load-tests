package InContactAPI

import java.util.{Date, Properties}

import io.gatling.core.Predef.Simulation
import javax.mail._
import javax.mail.internet._


class EmailSMPT(to: String, cc: String,
                bcc: String,
                from: String,
                subject: String,
                content: String,
                smtpHost: String){
  var message: Message = _
  message = createMessage
  message.setFrom(new InternetAddress(from))
  setToCcBccRecipients

  message.setSentDate(new Date())
  message.setSubject(subject)
  message.setText(content)

  // throws MessagingException
  def sendMessage() {
    Transport.send(message)
  }

  def createMessage: Message = {
    val properties = new Properties()
    properties.put("mail.smtp.host", smtpHost)
    properties.put("mail.smtp.port", "9100")
    val session = Session.getDefaultInstance(properties, null)
    new MimeMessage(session)
  }

  // throws AddressException, MessagingException
  def setToCcBccRecipients() {
    setMessageRecipients(to, Message.RecipientType.TO)
    if (cc != null) {
      setMessageRecipients(cc, Message.RecipientType.CC)
    }
    if (bcc != null) {
      setMessageRecipients(bcc, Message.RecipientType.BCC)
    }
  }

  // throws AddressException, MessagingException
  def setMessageRecipients(recipient: String, recipientType: Message.RecipientType) {
    // had to do the asInstanceOf[...] call here to make scala happy
    val addressArray = buildInternetAddressArray(recipient).asInstanceOf[Array[Address]]
    if ((addressArray != null) && (addressArray.length > 0)) {
      message.setRecipients(recipientType, addressArray)
    }
  }

  // throws AddressException
  def buildInternetAddressArray(address: String): Array[InternetAddress] = {
    // could test for a null or blank String but I'm letting parse just throw an exception
    InternetAddress.parse(address)
  }

}

