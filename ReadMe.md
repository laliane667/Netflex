🚀 Get started with Netflex: 🚀

Installation:

✅ Step 1: Download Netflex.zip and extract it where you want.

✅ Step 2: Open IntelliJ. Open existing project. Open Netflex. Then click on "File" > "Project Structure" > "Modules" > "+" > "JARs or directory" > 
           and add all jars there are inside src/include folder.

✅ Step 3: Download the multiple movies folders.
          https://www.mediafire.com/file/e8pepsd1yst4nf5/Movies-SEL1.zip/file
          https://www.mediafire.com/file/z5xkfe5af1jw26b/Movies-SEL2.zip/file
        
✅ Step 4: Extract the previously downloaded files, then drag and drop the content inside Netflex/src/resources/Movies (IMPORTANT: the content should be
           some folders named by movie title).

✅ Step 5: Open phpMyAdmin. Create a new database in the left panel. Name it "NetFlex". Click on the database you've created. Then click on "Import" at 
           the top of your screen. Select NetFlex.sql in the Netflex folder you've extracted at Step 1. Finally, import the database.

✅ Step 6: Don't close phpMyAdmin. Still in the NetFlex database, copy the server's name (ex: localhost:8889). Then in the file ConnectionController 
           in src/dependencies/Controller/ConnectionController, change the URL server with your server address. Also, change the username and password 
           if needed.
           
🎉 Installation finished, enjoy! 🎉
