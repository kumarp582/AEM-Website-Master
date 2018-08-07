# AEM Website Project

This a content package project generated using the AEM Multimodule Lazybones template.

## Back-end Build

This project uses Maven for building. Common commands:

From the root directory, run ``mvn -PautoInstallPackage clean install`` to build the bundle and content package and install to a CQ instance.

From the bundle directory, run ``mvn -PautoInstallBundle clean install`` to build *just* the bundle and install to a CQ instance.

## Front-end Build
Since maven compiles the sass and AEM handles a lot of the script concatenation, getting Gulp setup to work with the project ease front end development, providing automagic Sass compilation, JavaScript linting and slinging of updated front end resources (HTML, CSS/SCSS, JS) to AEM.

### Requirements
Install the front end tooling:
- [Node.js/NPM](https://nodejs.org/en/) 
- Install Gulp globally via: ``npm install -g gulp``
- In the command line, make your way to the project root and run the command: ``npm install``

### Front end tasks
- ``gulp watch`` - Watches for changes to JavaScript, CSS/Sass, and HTML files, runs any necessary subtasks and slings the results to AEM.
- ``gulp sass`` - Builds all Sass files and slings the compiled stylesheet and source maps to AEM.
- ``gulp js:lint`` - Runs all JavaScript files against JSHint and outputs the results to the console.


## Setting up local Apache Solr instance
1- Start Jetty. This will take sometime the first time, as Solr will be fetched from a Maven repository.

``$ cd solr.quickstart``

``$ mvn clean resources:resources jetty:run``

2- Open a browser and visit: http://localhost:8888/solr/

## Using with AEM Developer Tools for Eclipse

To use this project with the AEM Developer Tools for Eclipse, import the generated Maven projects via the Import:Maven:Existing Maven Projects wizard. Then enable the Content Package facet on the _content_ project by right-clicking on the project, then select Configure, then Convert to Content Package... In the resulting dialog, select _src/main/content_ as the Content Sync Root.

## Using with VLT

To use vlt with this project, first build and install the package to your local CQ instance as described above. Then cd to `content/src/main/content/jcr_root` and run

    vlt --credentials admin:admin checkout -f ../META-INF/vault/filter.xml --force http://localhost:4502/crx

Once the working copy is created, you can use the normal ``vlt up`` and ``vlt ci`` commands.

## Specifying CRX Host/Port

The CRX host and port can be specified on the command line with:
mvn -Dcrx.host=otherhost -Dcrx.port=5502 <goals>
