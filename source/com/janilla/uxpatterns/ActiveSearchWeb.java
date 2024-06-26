/*
 * MIT License
 *
 * Copyright (c) 2024 Diego Schivo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.janilla.uxpatterns;

import java.util.List;

import com.janilla.util.Util;
import com.janilla.web.Handle;
import com.janilla.web.Bind;
import com.janilla.web.Render;

public class ActiveSearchWeb {

	private static List<Contact> contacts = """
			Venus	Grimes	lectus.rutrum@Duisa.edu
			Fletcher	Owen	metus@Aenean.org
			William	Hale	eu.dolor@risusodio.edu
			TaShya	Cash	tincidunt.orci.quis@nuncnullavulputate.co.uk
			Kevyn	Hoover	tristique.pellentesque.tellus@Cumsociis.co.uk
			Jakeem	Walker	Morbi.vehicula.Pellentesque@faucibusorci.org
			Malcolm	Trujillo	sagittis@velit.edu
			Wynne	Rice	augue.id@felisorciadipiscing.edu
			Evangeline	Klein	adipiscing.lobortis@sem.org
			Jennifer	Russell	sapien.Aenean.massa@risus.com
			Rama	Freeman	Proin@quamPellentesquehabitant.net
			Jena	Mathis	non.cursus.non@Phaselluselit.com
			Alexandra	Maynard	porta.elit.a@anequeNullam.ca
			Tallulah	Haley	ligula@id.net
			Timon	Small	velit.Quisque.varius@gravidaPraesent.org
			Randall	Pena	facilisis@Donecconsectetuer.edu
			Conan	Vaughan	luctus.sit@Classaptenttaciti.edu
			Dora	Allen	est.arcu.ac@Vestibulumante.co.uk
			Aiko	Little	quam.dignissim@convallisest.net
			Jessamine	Bauer	taciti.sociosqu@nibhvulputatemauris.co.uk
			Gillian	Livingston	justo@atiaculisquis.com
			Laith	Nicholson	elit.pellentesque.a@diam.org
			Paloma	Alston	cursus@metus.org
			Freya	Dunn	Vestibulum.accumsan@metus.co.uk
			Griffin	Rice	justo@tortordictumeu.net
			Catherine	West	malesuada.augue@elementum.com
			Jena	Chambers	erat.Etiam.vestibulum@quamelementumat.net
			Neil	Rodriguez	enim@facilisis.com
			Freya	Charles	metus@nec.net
			Anastasia	Strong	sit@vitae.edu
			Bell	Simon	mollis.nec.cursus@disparturientmontes.ca
			Minerva	Allison	Donec@nequeIn.edu
			Yoko	Dawson	neque.sed@semper.net
			Nadine	Justice	netus@et.edu
			Hoyt	Rosa	Nullam.ut.nisi@Aliquam.co.uk
			Shafira	Noel	tincidunt.nunc@non.edu
			Jin	Nunez	porttitor.tellus.non@venenatisamagna.net
			Barbara	Gay	est.congue.a@elit.com
			Riley	Hammond	tempor.diam@sodalesnisi.net
			Molly	Fulton	semper@Naminterdumenim.net
			Dexter	Owen	non.ante@odiosagittissemper.ca
			Kuame	Merritt	ornare.placerat.orci@nisinibh.ca
			Maggie	Delgado	Nam.ligula.elit@Cum.org
			Hanae	Washington	nec.euismod@adipiscingelit.org
			Jonah	Cherry	ridiculus.mus.Proin@quispede.edu
			Cheyenne	Munoz	at@molestiesodalesMauris.edu
			India	Mack	sem.mollis@Inmi.co.uk
			Lael	Mcneil	porttitor@risusDonecegestas.com
			Jillian	Mckay	vulputate.eu.odio@amagnaLorem.co.uk
			Shaine	Wright	malesuada@pharetraQuisqueac.org
			Keane	Richmond	nostra.per.inceptos@euismodurna.org
			Samuel	Davis	felis@euenim.com
			Zelenia	Sheppard	Quisque.nonummy@antelectusconvallis.org
			Giacomo	Cole	aliquet.libero@urnaUttincidunt.ca
			Mason	Hinton	est@Nunc.co.uk
			Katelyn	Koch	velit.Aliquam@Suspendisse.edu
			Olga	Spencer	faucibus@Praesenteudui.net
			Erasmus	Strong	dignissim.lacus@euarcu.net
			Regan	Cline	vitae.erat.vel@lacusEtiambibendum.co.uk
			Stone	Holt	eget.mollis.lectus@Aeneanegestas.ca
			Deanna	Branch	turpis@estMauris.net
			Rana	Green	metus@conguea.edu
			Caryn	Henson	Donec.sollicitudin.adipiscing@sed.net
			Clarke	Stein	nec@mollis.co.uk
			Kelsie	Porter	Cum@gravidaAliquam.com
			Cooper	Pugh	Quisque.ornare.tortor@dictum.co.uk
			Paul	Spencer	ac@InfaucibusMorbi.com
			Cassady	Farrell	Suspendisse.non@venenatisa.net
			Sydnee	Velazquez	mollis@loremfringillaornare.com
			Felix	Boyle	id.libero.Donec@aauctor.org
			Ryder	House	molestie@natoquepenatibus.org
			Hadley	Holcomb	penatibus@nisi.ca
			Marsden	Nunez	Nulla.eget.metus@facilisisvitaeorci.org
			Alana	Powell	non.lobortis.quis@interdumfeugiatSed.net
			Dennis	Wyatt	Morbi.non@nibhQuisquenonummy.ca
			Karleigh	Walton	nascetur.ridiculus@quamdignissimpharetra.com
			Brielle	Donovan	placerat@at.edu
			Donna	Dickerson	lacus.pede.sagittis@lacusvestibulum.com
			Eagan	Pate	est.Nunc@cursusNunc.ca
			Carlos	Ramsey	est.ac.facilisis@duinec.co.uk
			Regan	Murphy	lectus.Cum@aptent.com
			Claudia	Spence	Nunc.lectus.pede@aceleifend.co.uk
			Genevieve	Parker	ultrices@inaliquetlobortis.net
			Marshall	Allison	erat.semper.rutrum@odio.org
			Reuben	Davis	Donec@auctorodio.edu
			Ralph	Doyle	pede.Suspendisse.dui@Curabitur.org
			Constance	Gilliam	mollis@Nulla.edu
			Serina	Jacobson	dictum.augue@ipsum.net
			Charity	Byrd	convallis.ante.lectus@scelerisquemollisPhasellus.co.uk
			Hyatt	Bird	enim.Nunc.ut@nonmagnaNam.com
			Brent	Dunn	ac.sem@nuncid.com
			Casey	Bonner	id@ornareelitelit.edu
			Hakeem	Gill	dis@nonummyipsumnon.org
			Stewart	Meadows	Nunc.pulvinar.arcu@convallisdolorQuisque.net
			Nomlanga	Wooten	inceptos@turpisegestas.ca
			Sebastian	Watts	Sed.diam.lorem@lorem.co.uk
			Chelsea	Larsen	ligula@Nam.net
			Cameron	Humphrey	placerat@id.org
			Juliet	Bush	consectetuer.euismod@vitaeeratVivamus.co.uk
			Caryn	Hooper	eu.enim.Etiam@ridiculus.org""".lines().map(l -> {
		var ss = l.split("\t");
		return new Contact(Long.valueOf(1), ss[0], ss[1], ss[2]);
	}).toList();

	@Handle(method = "GET", path = "/active-search")
	public @Render("ActiveSearch.html") Object getPage() {
		return "page";
	}

	@Handle(method = "POST", path = "/active-search/search")
	public ResultPage searchContacts(@Bind("search") String search) throws InterruptedException {
		Thread.sleep(500);
		var cc = contacts.stream();
		if (search != null && !search.isBlank())
			cc = cc.filter(x -> Util.startsWithIgnoreCase(x.firstName(), search)
					|| Util.startsWithIgnoreCase(x.lastName(), search) || Util.startsWithIgnoreCase(x.email(), search));
		return new ResultPage(cc.toList());
	}

	@Render("ActiveSearch-Page.html")
	public record ResultPage(List<@Render("ActiveSearch-Row.html") Contact> contacts) {
	}
}
