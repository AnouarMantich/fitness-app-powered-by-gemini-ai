#!/bin/sh

# Attente supplémentaire de 30 secondes
echo "⏳ Attente supplémentaire de 30 secondes avant démarrage..."
sleep 30

# Puis lancement de l'application
exec java -jar app.jar