reg=/^(?:(?:https?|ftp):\/\/)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:\/\S*)?$/;

function test(){
	 let x = document.getElementById("url").value;
			 
      if (reg.test(x)) {
                document.getElementById("urlCheck").innerText = "";
                postForURL(x);
                postForQR(x);
            } else {
                document.getElementById("urlCheck").innerText = "URL is invalid";
                document.getElementById('urlCheck').style.display = 'inline';
                document.getElementById("shortRes").innerText="";
                document.getElementById('QR').src="";
                document.getElementById('link').style.display = 'none';

            }
}


async function postForURL(url){
	 let result = await axios.post("/shortUrl",
        {
          url:url
        }
      );
      
     document.getElementById("shortRes").innerHTML = "Shortend URL is "+`<a href="${result.data}" target="_blank" class="">${result.data}</a>`;
}

async function postForQR(url){
	let result = await axios.post("/QRUrl",
        {
          url:url,
          
        }, { responseType: 'blob' }
      );
     
	document.getElementById('QR').src=window.URL.createObjectURL(result.data);
	document.getElementById('link').style.display = 'inline';
	document.getElementById('link').href=window.URL.createObjectURL(result.data);
	document.getElementById('link').download="QR";
}

