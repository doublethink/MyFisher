# MyFisher
Android Application exploring Snapper Smartcard security

Note: Please excuse the poor coding - particularly in the BinaryTools class.
Some of this code was directly cut and paste from Dex2Jar + JD-GUI, I have made some of the hex encode/decode readable
but not all. This app is partly reverse engineered from the MySnapper app.

Findings from exploring the app:
-Snapper for Android uses IsoDep to transceive APDU formatted byte arrays to the card
-Communication with the card is done with arrays of bytes, sometimes decoded from hex or base64->hex
-Base64 encryption is used to send card responses and replies to Snapper servers.

Reading the card:
- Call ApduDefs.CARD_BALANCE for the card balance, the first four bytes is the balance as a binary Long.
- Call ApduDefs.PURSE_INFO for card ID, this is BCD "encoded"

Writing to the card: --Note this is informational only and will not top up your card
- Two essential card calls are needed to successfully top up.
- The first is to add the top up amount (as a binary) into the last two bytes of the ApduDefs.INIT_DATA template.
- Send the card response + the response of ApduDefs.PURSE_INFO to the Snapper servers as a Reload service (using SOAP and encoded as base64).
- Snapper will check if your card has any top ups pending and send you a base64 encoded ReloadResponse which is transceived directly to the card.

Take homes:
- Snapper do not keep the logic to write money to the card on the client app.
- It may be possible to reverse engineer the ReloadResponse array from all the info that is sent to the server (I have been unsuccessful)
- The response of INIT_DATA is always different - even with the same top up amount encoded in it and card balance -
  this may suggest a timestamp or nonce is in play here.
- I am not an NFC expert.
