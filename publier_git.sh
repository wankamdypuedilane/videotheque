#!/usr/bin/env bash
# Publie le projet videotheque sur un depot Git distant.
# Usage : ./publier_git.sh https://github.com/ton-compte/videotheque.git
# A lancer depuis la racine du projet (~/IdeaProjects/videotheque).
set -e

if [ -z "$1" ]; then
  echo "Usage : $0 <url-du-depot-distant>"
  exit 1
fi

if [ ! -f pom.xml ]; then
  echo "Erreur : lance ce script depuis la racine du projet (pom.xml introuvable)."
  exit 1
fi

# Initialise le depot s'il n'existe pas encore
if [ ! -d .git ]; then
  git init
fi

git add .
echo "--- Fichiers qui seront commites ---"
git status --short
echo "------------------------------------"
echo "Verifie qu'il n'y a ni target/ ni .idea/ dans la liste ci-dessus."
read -p "Continuer ? (o/N) " reponse
[ "$reponse" = "o" ] || exit 0

git commit -m "Projet videotheque : API REST Spring Boot (CRUD, associations, validation, tests)"
git branch -M main

if git remote | grep -q origin; then
  git remote set-url origin "$1"
else
  git remote add origin "$1"
fi

git push -u origin main
echo "Depot publie : $1"
