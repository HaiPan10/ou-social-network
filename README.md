# ou-social-network

docker build -t ou-social-network:latest .
docker tag ou-social-network:latest phonglai0809/ou-social-network:latest
docker push phonglai0809/ou-social-network:latest

docker build -t react-app:latest .
docker tag react-app:latest phonglai0809/react-app:latest
docker push phonglai0809/react-app:latest

Edit the Reactjs package.json
  "scripts": {
    "start": "export PORT=80 && react-scripts start",
    "build": "react-scripts build",
    "test": "react-scripts test",
    "eject": "react-scripts eject"
  },
  