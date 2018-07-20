build:
	docker build -t jrat-build .
	docker run -v ${PWD}/deploy:/deploy jrat-build

