# Document Binder Utility
 
## Overview
Simple utility for working with tree structured folder like structures.

## Rationale
Initially created to simplify the creation of tree structure representation that I needed to return from a REST api to support Angular Materials MatTree.

Basically:

Given a collection of documents like:
```java
public class MyDocumentEntity {
    private Long id;
    private LocalDate documentDate;
    private DocumentType documentType;
    private String name;
    private String description;
    private String filename;
    private String contentType;
    private Long fileSizeInBytes;
    private Blob file;
    ...
}
```

Create a tree structure representation by adding documents, specifying their full path in the tree and have the 
nodes created automatically:
```java

// create a binder with some docs
Binder binder = new Binder();
binder.add(new BinderDocument("Doc1"), "DOCS/2017/01");
binder.add(new BinderDocument("Doc2"), "DOCS/2017/02");
binder.add(new BinderDocument("Doc3"), "DOCS/2019/12");
binder.add(new BinderDocument("Img1"), "IMAGES/2019/01");

binder.getNumberOfDocuments(); // 4

// get doc3 starting at binder root
BinderDocument doc3 = (BinderDocument) binder.getNodes().get(0) // DOCS folder
                                             .getChildren().get(1) // DOCS/2019 folder
                                             .getChildren().get(0) // DOCS/2019/12 folder
                                             .getChildren().get(0); // Doc3 document

// from doc3 get DOCS/2019 folder
BinderFolder docs2019 = (BinderFolder) doc3.getParent() // DOCS/2019/12 folder
                                           .getParent(); // DOCS/2019 folder

// add doc4 to DOCS/2019
docs2019.add(new BinderDocument("Doc4"); // adds Doc4 to DOCS/2019 folder

// starting at binder get directory at path 
BinderFolder sameFolder = binder.getFolderAtPath("DOCS/2019");
sameFolder.getChildren().get(0); // DOCS/2019/12 folder
sameFolder.getChildren().get(1); // DOCS/2019/12/Doc4 document





```
