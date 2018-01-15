package org.anirudh.redquark.lucenestarter.document;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

/**
 * This class is used to write documents to the Lucene index
 * 
 * @author anirshar
 *
 */
public class LuceneWriteIndex {

	/**
	 * Setting the path where the indices will be created
	 */
	private static final String INDEX_DIR = "C:\\Users\\anirshar\\Documents\\oks_workspace\\lucene-starter\\index_directory";

	public static void main(String[] args) {

		try {
			/**
			 * Creating an IndexWriter. This will be used to create and manage index.
			 */
			IndexWriter writer = createWriter();

			/**
			 * Creating a list of documents.
			 * 
			 * Documents are the unit of indexing and search. A Document is a set of fields.
			 * Each field has a name and a textual value. A field may be stored with the
			 * document, in which case it is returned with search hits on the document. Thus
			 * each document should typically contain one or more stored fields which
			 * uniquely identify it.
			 */
			List<Document> documents = new ArrayList<>();

			/**
			 * Creating some separate documents
			 */
			Document documentA = createDocument(1, "Bruce", "Wayne", "www.gotham.com");
			documents.add(documentA);

			Document documentB = createDocument(2, "Clark", "Kent", "www.metropolis.com");
			documents.add(documentB);

			/**
			 * Cleaning everything first
			 */
			writer.deleteAll();

			/**
			 * Adding documents to the IndexWriter instance
			 */
			writer.addDocuments(documents);

			/**
			 * Committing the writer
			 */
			writer.commit();

			/**
			 * Closing the writer
			 */
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static IndexWriter createWriter() {
		try {
			/**
			 * FSDirectory is the base class for Directory implementations that stores index
			 * files in the file system.
			 * 
			 * FSDirectory resolves the given Path when calling the open() method to a
			 * canonical / real path to ensure it can correctly lock the index directory and
			 * no other process can interfere with changing possible symlinks to the index
			 * directory in between
			 */
			FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));

			/**
			 * Holds all the configuration that is used to create an IndexWriter. Once
			 * IndexWriter has been created with this object, changes to this object will
			 * not affect the IndexWriter instance.
			 * 
			 * The StandardAnalyzer class filters StandardTokenizer with StandardFilter,
			 * LowerCaseFilter and StopFilter, using a list of English stop words.
			 */
			IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());

			/**
			 * An IndexWriter creates and maintains an index. The constructor takes
			 * FSDirectory and IndexWriterConfig as arguments. Please note that after the
			 * writer is created, the given configuration instance cannot be passed to
			 * another writer
			 */
			IndexWriter writer = new IndexWriter(dir, config);

			return writer;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method is responsible for creating some documents.
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param website
	 * @return Document
	 */
	private static Document createDocument(Integer id, String firstName, String lastName, String website) {

		try {
			/**
			 * Creating a new instance of document
			 */
			Document document = new Document();

			/**
			 * Adding some fields to the document
			 */
			document.add(new StringField("id", id.toString(), Field.Store.YES));
			document.add(new TextField("firstName", firstName, Field.Store.YES));
			document.add(new TextField("lastName", lastName, Field.Store.YES));
			document.add(new TextField("website", website, Field.Store.YES));

			return document;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
