

function ajaxRequest(method, url, handlerf, content, requestHeadersf){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){eval(handlerf + "(xhttp)");};
    xhttp.open(method, url);
    if(requestHeadersf === 'undefined'){}
    else{eval(requestHeadersf + "(xhttp)");}
    if(method == "POST"){
        xhttp.send(content);
    }
    else{
        xhttp.send();
    }
}

function AzureSpeechText(data){
    var method = "POST";
    var url = "https://westus.stt.speech.microsoft.com/speech/recognition/conversation/cognitiveservices/v1?language=en-US";
    var handlerFunction = "AzureSpeechTextHandlerf";
    var requestHeaders = "AzureSpeechTextHeadersf";
    
    ajaxRequest(method, url, handlerFunction, data, requestHeaders);
}

function AzureSpeechTextHeadersf(xhttp){
    var CONFIG = require('./config.json');
    xhttp.setRequestHeader("Accept", "application/json;text/xml");
    xhttp.setRequestHeader("Content-type", "audio/wav; codec=audio/pcm; samplerate=16000");
    xhttp.setRequestHeader("Ocp-Apim-Subscription-Key", CONFIG.APISubKey);
    xhttp.setRequestHeader("Host", CONFIG.host);
}

function AzureSpeechTextHandlerf(xhttp){
    if (xhttp.readyState == 4 && xhttp.status == 200){
        console.log(JSON.parse(xhttp.responseText).DisplayText);
    }
    else if (xhttp.readyState == 4 && xhttp.status == 400){
        console.log("ERROR: Language code not provided or is not a supported language; invalid audio file.");
    }
    else if (xhttp.readyState == 4 && xhttp.status == 401){
        console.log("ERROR: Subscription key or authorization token is invalid in the specified region, or invalid endpoint.");
    }
    else if(xhttp.readyState == 4 && xhttp.status == 403){
        console.log("ERROR: Missing subscription key or authorization token.");
    }
}

function start(){
    var audio = new Audio("Recording.ogg");
    audio.play();
    AzureSpeechText(audio);
}

start();