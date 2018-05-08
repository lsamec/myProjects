insert into RAZINA_PRAVA (razina_prava_id,razina_prava_vrsta) values (1,'admin');
insert into RAZINA_PRAVA (razina_prava_id,razina_prava_vrsta) values (2,'sluzbena_osoba');
insert into RAZINA_PRAVA (razina_prava_id,razina_prava_vrsta) values (3,'tehnicka_komisija');
insert into RAZINA_PRAVA (razina_prava_id,razina_prava_vrsta) values (4,'natjecatelj');

insert into KONFIGURACIJA (konfiguracija_id,proracun,status_simulatora,odobren_konf,postoji_sastav_ekipa,igraci_ok,moze_sim,odobrena_reg,zapoceta_liga,just_a_konf_holder) values (1,null,null,null,false,false,null,null,null,true);

insert into KORISNIK (korisnik_id,korisnicko_ime,lozinka,razina_prava) values (-1,'admin','admin',1);
insert into KORISNIK (korisnik_id,korisnicko_ime,lozinka,razina_prava) values (-2,'sluzbenaOsoba','sluzbenaOsoba',2);
insert into KORISNIK (korisnik_id,korisnicko_ime,lozinka,razina_prava) values (-3,'tehKom','tehKom',3);

insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (1,'postignut_kos',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (2,'kos_iz_slobodnog_bacanja',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (3,'trica',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (4,'skok',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (5,'oduzimanje_lopte',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (6,'dodavanje',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (7,'blokada',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (8,'najbolji_igrac_utakmice',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (9,'izgubljena_lopta',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (10,'promaseno_slobodno_bacanje',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (11,'promasen_sut_za_dva',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (12,'promasena_trica',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (13,'tehnicka_pogreska',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (14,'osobna_pogreska',null);
insert into DOGADAJ (dogadaj_id,ime_dogadaj,vrijednost) values (15,'iskljucenje',null);

insert into POZICIJA (pozicija_id,ime_pozicija) values (1,'organizator');
insert into POZICIJA (pozicija_id,ime_pozicija) values (2,'bek');
insert into POZICIJA (pozicija_id,ime_pozicija) values (3,'krilo');
insert into POZICIJA (pozicija_id,ime_pozicija) values (4,'krilni centar');
insert into POZICIJA (pozicija_id,ime_pozicija) values (5,'centar');

insert into DRZAVA (drzava_id,ime_drzava) values (1,'država1');
insert into DRZAVA (drzava_id,ime_drzava) values (2,'država2');
insert into DRZAVA (drzava_id,ime_drzava) values (3,'država3');
insert into DRZAVA (drzava_id,ime_drzava) values (4,'država4');
insert into DRZAVA (drzava_id,ime_drzava) values (5,'država5');
insert into DRZAVA (drzava_id,ime_drzava) values (6,'država6');
insert into DRZAVA (drzava_id,ime_drzava) values (7,'država7');
insert into DRZAVA (drzava_id,ime_drzava) values (8,'država8');

insert into EKIPA (ekipa_id,ime_ekipa,bodovi,pobjedene,izgubljene,nerjesene) values (1,'ekipa01',0,0,0,0);
insert into EKIPA (ekipa_id,ime_ekipa,bodovi,pobjedene,izgubljene,nerjesene) values (2,'ekipa02',0,0,0,0);
insert into EKIPA (ekipa_id,ime_ekipa,bodovi,pobjedene,izgubljene,nerjesene) values (3,'ekipa03',0,0,0,0);
insert into EKIPA (ekipa_id,ime_ekipa,bodovi,pobjedene,izgubljene,nerjesene) values (4,'ekipa04',0,0,0,0);
insert into EKIPA (ekipa_id,ime_ekipa,bodovi,pobjedene,izgubljene,nerjesene) values (5,'ekipa05',0,0,0,0);
insert into EKIPA (ekipa_id,ime_ekipa,bodovi,pobjedene,izgubljene,nerjesene) values (6,'ekipa06',0,0,0,0);
insert into EKIPA (ekipa_id,ime_ekipa,bodovi,pobjedene,izgubljene,nerjesene) values (7,'ekipa07',0,0,0,0);
insert into EKIPA (ekipa_id,ime_ekipa,bodovi,pobjedene,izgubljene,nerjesene) values (8,'ekipa08',0,0,0,0);
insert into EKIPA (ekipa_id,ime_ekipa,bodovi,pobjedene,izgubljene,nerjesene) values (9,'ekipa09',0,0,0,0);
insert into EKIPA (ekipa_id,ime_ekipa,bodovi,pobjedene,izgubljene,nerjesene) values (10,'ekipa10',0,0,0,0);
insert into EKIPA (ekipa_id,ime_ekipa,bodovi,pobjedene,izgubljene,nerjesene) values (11,'ekipa11',0,0,0,0);
insert into EKIPA (ekipa_id,ime_ekipa,bodovi,pobjedene,izgubljene,nerjesene) values (12,'ekipa12',0,0,0,0);

insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(1,1,12,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(2,2,11,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(3,3,10,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(4,4,9,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(5,5,8,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(6,6,7,null,null);

insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(7,12,7,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(8,8,6,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(9,9,5,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(10,10,4,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(11,11,3,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(12,1,2,null,null);

insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(13,2,12,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(14,3,1,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(15,4,11,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(16,5,10,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(17,6,9,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(18,7,8,null,null);

insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(19,12,8,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(20,9,7,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(21,10,6,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(22,11,5,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(23,1,4,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(24,2,3,null,null);

insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(25,3,12,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(26,4,2,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(27,5,1,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(28,6,11,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(29,7,10,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(30,8,9,null,null);

insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(31,12,9,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(32,10,8,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(33,11,7,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(34,1,6,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(35,2,5,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(36,3,4,null,null);

insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(37,4,12,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(38,5,3,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(39,6,2,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(40,7,1,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(41,8,11,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(42,9,10,null,null);

insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(43,12,10,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(44,11,9,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(45,1,8,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(46,2,7,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(47,3,6,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(48,4,5,null,null);

insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(49,5,12,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(50,6,4,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(51,7,3,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(52,8,2,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(53,9,1,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(54,10,11,null,null);

insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(55,12,11,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(56,1,10,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(57,2,9,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(58,3,8,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(59,4,7,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(60,5,6,null,null);

insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(61,6,12,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(62,7,5,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(63,8,4,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(64,9,3,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(65,10,2,null,null);
insert into UTAKMICA (utakmica_id,ekipa_domacin,ekipa_gost,igrac_utakmice,rezultat) values(66,11,1,null,null);


