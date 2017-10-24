# Scripts

### [clean.sh](clean.sh)

Removes unwanted files from ```Controller.jar``` and ```Stub.jar``` such as

- ```Debug.class```
- ```.project```
- ```.gitignore```
- ```.classpath```
- ```*.md```

Useful when building artifacts

### [update_changelog.sh](update_changelog.sh)

Gets the changelog in markdown from web service located at ```https://jrat.io/api/changelog_markdown.php``` and puts it in ```CHANGELOG.md```

(Requires curl)

### [statistics.sh](statistics.sh)

Shows total lines of code, total commits and commits for each member
