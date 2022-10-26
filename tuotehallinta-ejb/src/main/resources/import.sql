--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--


insert into Kirjailija(id, etunimi, sukunimi) values (1, 'Bert', 'Bates'),(2, 'Kathy', 'Sierra'),(3, 'Joshua', 'Block'),(4, 'David R.', 'Heffelfinger'),(5, 'Rhuan', 'Rocha'), (6, 'Joao', 'Purificacao'), (7, 'Daniel', 'Bryant'), (8, 'Abraham', 'Marin-Perez')
insert into Kustantaja(id, nimi, website) values (1, 'O''Reilly Media', 'https://www.oreilly.com/'),(2, 'Addison-Wesley Professional', 'https://www.pearson.com/us/higher-education/series/Addison-Wesley-Professional-Computing-Series/334583.html'), (3, 'Packt Publishing', 'https://www.packtpub.com/')

insert into Kirja(id, nimi, ISBN10, ISBN13, kieli, hinta, julkaistu, editio, kustantaja) values(1, 'Head First Java', '0596009208', '978-0596009205', 'englanti', 37.0, '2005-02-05', '2nd', 1), (2, 'Effective Java', '0134685997', '978-0134685991', 'englanti', 25.9, '2017-12-27', '3rd', 2), (3, 'Java EE 8 Application Development', '1788293673', '978-1788293679', 'englanti', 63.55, '2017-12-12', '2nd', 3), (4, 'Java EE 8 Design Patterns and Best Practices', '1788830628', '978-1788830621', 'englanti', 15.2, '2018-08-10', '1st', 3), (5, 'Continuous Delivery in Java', '1491986026', '978-1491986028', 'englanti', 54.0, '2018-11-23', '1st', 1)
insert into Kirja(id, nimi, ISBN10, ISBN13, kieli, hinta, julkaistu, editio, kustantaja) values(6, 'Java Puzzlers', '032133678X', NULL, 'englanti', 11.12, '2005-06-24', '1st', 2)

insert into kirja_kirjailija(kirja_id, kirjailija_id) values(1, 1), (1, 2), (2, 3), (3, 4), (4, 5), (4, 6), (5, 7), (5, 8), (6, 3)
