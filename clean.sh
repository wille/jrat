FILES=(Controller/Controller.jar Controller/files/Stub.jar)
ENTRIES=(Debug.class .project .gitignore .classpath *.md)

for i in "${FILES[@]}"
do
   echo Processing $i
   zip -d $i ${ENTRIES[@]}
done