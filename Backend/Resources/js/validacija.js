var Validacija = (function() {
    var maxGrupa = 7;
    var trenutniSemestar = 0;

    var emailValidation = function(facultyEmail) {
        var pattern = /^[a-zA-Z0-9._-]+@\.unsa\.ba$/;

        //console.log("Email: ", pattern.test(facultyEmail));
        if(!pattern.test(facultyEmail))
        {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.dodajPoruku(0);
            Poruke.ispisiGreske();
        }
        else {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.ocistiGresku(0);
        }
        return pattern.test(facultyEmail);
    }

    var validirajIndex = function(brojIndexa) {
        var pattern = /^1\d{4,4}$/;

        //console.log("Index: ", pattern.test(brojIndexa));

        if(!pattern.test(brojIndexa))
        {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.dodajPoruku(1);
            Poruke.ispisiGreske();
        }
        else {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.ocistiGresku(1);
        }
        return pattern.test(brojIndexa);
    }

    var validirajGrupu = function(brojGrupe){
        if(brojGrupe >= 1 && brojGrupe <= maxGrupa) {
            //console.log("Grupa: true");
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.ocistiGresku(2);
            return true;
        }
        else {
            //	console.log("Grupa: false");
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.dodajPoruku(2);
            Poruke.ispisiGreske();
            return false;
        }
    }

    var validirajAkGod = function(akGodina){
        var inputPattern = /^20\d{2}\/20\d{2}$/;
        var isValidInput = inputPattern.test(akGodina);
        var prviSemestar, drugiSemestar;
        [prviSemestar, drugiSemestar] = akGodina.split('/').map(Number);

        if(isValidInput &&(drugiSemestar - prviSemestar == 1)) {
            var datum = new Date();
            var godina = datum.getFullYear();

            if(trenutniSemestar == 0 && prviSemestar == godina || trenutniSemestar == 1 && drugiSemestar == godina){
                //console.log("Akgod: true");
                Poruke.postaviIdDiva("ispisiPoruke");
                Poruke.ocistiGresku(3);
                return true;
            }
        }

        Poruke.postaviIdDiva("ispisiPoruke");
        Poruke.dodajPoruku(3);
        Poruke.ispisiGreske();
        //console.log("Akgod: false");
        return true;
    }

    var validirajPassword = function(password) {
        var pattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{7,20}$/;

        //console.log("Password: ", pattern.test(pattern));

        if(!pattern.test(password))
        {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.dodajPoruku(4);
            Poruke.ispisiGreske();
        }
        else {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.ocistiGresku(4);
        }
        return pattern.test(pattern);
    }

    var validirajPotvrdu = function(password1, password2){
        //console.log("Potvrda: ", password1 == password2);
        if(password1 != password2)
        {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.dodajPoruku(5);
            Poruke.ispisiGreske();
        }
        else {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.ocistiGresku(5);
        }
        return password1 == password2;
    }

    var validirajBitbucketURL = function(bitbucketURL) {
        var pattern = /^(https?:\/\/).*\w+@bitbucket\.org\/.*\w\/.*\w\.git$/;
        //console.log("url: ", pattern.test(bitbucketURL));
        if(!pattern.test(bitbucketURL))
        {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.dodajPoruku(6);
            Poruke.ispisiGreske();
        }
        else {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.ocistiGresku(6);
        }
        return pattern.test(bitbucketURL);
    }

    var validirajBitbucketSSH = function(bitbucketSSH){
        var pattern = /git@bitbucket\.org:.*\w\/.*\w\.git$/;
        //	console.log("SSH", pattern.test(bitbucketSSH));

        if(!pattern.test(bitbucketSSH))
        {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.dodajPoruku(7);
            Poruke.ispisiGreske();
        }
        else {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.ocistiGresku(7);
        }
        return pattern.test(bitbucketSSH);
    }

    var validirajNazivRepozitorija = function(pattern, repo) {
        if(pattern == null){
            pattern = /^wt[pP]rojekat\d{5}/;
        }

        //console.log("Repo: ", pattern.test(repo));
        if(!pattern.test(repo))
        {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.dodajPoruku(8);
            Poruke.ispisiGreske();
        }
        else {
            Poruke.postaviIdDiva("ispisiPoruke");
            Poruke.ocistiGresku(8);
        }
        return pattern.test(repo);
    }

    var validirajImeiPrezime = function(imePrezime){
        var pattern = /^[A-Z|ÄŒ|Ä†|Å½|Å |Ä][A-Z|a-z|Ä|Ä‡|Å¾|Å¡|Ä‘|ÄŒ|Ä†|Å½|Å |Ä'-]{2,12}/;

        var array = imePrezime.split(" ");
        for(var i = 0; i < array.length; i++) {

            if(!pattern.test(array[i]) || array[i].length == 1 || /^['-]/.test(array[i][0])) {
                Poruke.postaviIdDiva("ispisiPoruke");
                Poruke.dodajPoruku(9);
                Poruke.ispisiGreske();
                console.log(!pattern.test(array[i]));
                console.log(array.length);
                console.log(/^['-]/.test(array[i][0]));
                console.log(array[i]);
                return false;
            }
        }
        Poruke.postaviIdDiva("ispisiPoruke");
        Poruke.ocistiGresku(9);
        return true;
    }




    return {
        validirajFakultetski : validirajFakultetski,
        validirajIndex : validirajIndex,
        validirajGrupu : validirajGrupu,
        validirajAkGod : validirajAkGod,
        validirajPassword : validirajPassword,
        validirajPotvrdu : validirajPotvrdu,
        validirajBitbucketURL : validirajBitbucketURL,
        validirajBitbucketSSH : validirajBitbucketSSH,
        validirajNazivRepozitorija : validirajNazivRepozitorija,
        validirajImeiPrezime : validirajImeiPrezime
    }

}());

function showRegisterStudent() {
    document.getElementById("studentRegistration").style.display = "block";
    document.getElementById("formaNastavnik").style.display = "none";

}

function showRegisterProf() {
    document.getElementById("formaNastavnik").style.display = "block";
    document.getElementById("studentRegistration").style.display = "none";
}

function clearForm(formtToClear){
    if(formtToClear == 1){
        var formElements = document.getElementById("studentRegistration").elements;
        document.getElementById("studentRegistration").reset();
    }
    else{
        var formElements = document.getElementById("formaNastavnik").elements;
        document.getElementById("formaNastavnik").reset();

    }
    Poruke.postaviIdDiva("ispisiPoruke");
    for(var i = 0; i < formElements.length; i++){
        if(i < 10) Poruke.ocistiGresku(i);

        var temp = formElements[i].type.toLowerCase();
        switch(temp) {
            case "text":
            case "password":
            case "email":
                formElements[i].value = "";
                break;
            default:
                break;
        }
    }
}
