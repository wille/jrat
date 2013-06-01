Dim message, sapi
message= "%TEXT%"
Set sapi = CreateObject("sapi.spvoice")
sapi.Speak message