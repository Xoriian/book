#!/bin/bash

javac fr/wargame/controleur/main/Main.java
jar cfm wargame.jar manifest fr plateau.txt audio/ sprites/
find . -name "*.class" | xargs rm -f

cp wargame.jar /tmp/
cd /tmp
java -jar wargame.jar
cd -
