/*
 * Copyright (c) 2011, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package controller;
 
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Author;
import model.Book;
import model.Periodical;
import dataAccess.DataAccess;
import dataAccess.DataAccessFacade;
import dataAccess.StorageType;
 
public class PublciationController extends BaseController{
	@FXML private TextField ID;
    @FXML private TextField ISBN;
    @FXML private TextField title;
    @FXML private TextField maxCheckoutLength;
    @FXML private Label LbID;
    @FXML private Label LbISBN;
    @FXML private Button BtnAddAuthor;
    
    @FXML private CheckBox AddBook;
    @FXML private CheckBox AddPeriodical;

    //private Publication.PUBTYPE pubtype = Publication.PUBTYPE.BOOK;
    private StorageType storageType =  StorageType.BOOK;

    static private PublciationController instance=null;   
    static public PublciationController getInstance() {
    	if (null==instance) {
			instance = new PublciationController();
		}
    	return instance;
    }
    
    @FXML protected void GePublicationData(ActionEvent event) {
    	switch (storageType) {
		case BOOK:
			saveNewBook();
			//return dataAccess.saveNewBook(book);
			break;
		case PERIODICAL:
			savePeriodical();
		default:
			break;
		}

    }
    
    @FXML private boolean savePeriodical() {
    	String IssueNumber =  ID.getText();
    	String strtitle =  title.getText();
    	int nmaxCheckoutLength =  Integer.valueOf(maxCheckoutLength.getText());	
    	Periodical periodical = new Periodical(IssueNumber,strtitle,nmaxCheckoutLength);
    	AuthorController.ResetCurAuthors();
    	DataAccess dataAccess = new DataAccessFacade();
		return dataAccess.saveNewPeriodical(periodical);
    }
    
    private boolean saveNewBook() {
    	int nID =  Integer.valueOf(ID.getText());
    	String strISBN =  ISBN.getText();
    	String strtitle =  title.getText();
    	int nmaxCheckoutLength =  Integer.valueOf(maxCheckoutLength.getText());	
    	List<Author> authors = new ArrayList<Author>(AuthorController.getCurAuthors());
    	if (authors.size()<1) {
			System.err.println("add author first!");
			return false;
		}	
    	Book book = new Book(nID,strISBN,strtitle,nmaxCheckoutLength);
    	AuthorController.ResetCurAuthors();
    	DataAccess dataAccess = new DataAccessFacade();
		return dataAccess.saveNewBook(book);
    }
    
    @FXML protected void openAuthorUI(ActionEvent event) {
    	AuthorController.getInstance().openAuthorUI(stage);
    }
    
    @FXML protected void setBookType(ActionEvent event) {
    	storageType =  StorageType.BOOK;
    	AddPeriodical.setSelected(false);
    	LbID.setText("ID");
    	ISBN.setVisible(true);
    	LbISBN.setVisible(true);
    	BtnAddAuthor.setDisable(false);
    }
    
    @FXML protected void SelectPeriodicalType(ActionEvent event) {
    	storageType =  StorageType.PERIODICAL;
    	LbID.setText("IssueNo");
    	AddBook.setSelected(false);
    	ISBN.setVisible(false);
    	LbISBN.setVisible(false);
    	BtnAddAuthor.setDisable(true);
    }
    
    public void openPublciationUI(Stage superStage) {
    	if (stage!=null && stage.isShowing()) {
    		System.out.println("Already open the author UI!");
    		return;
		}
    	//stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("./Publication.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("AddPublication");
            stage.setScene(scene);
            //stage.initModality(Modality.WINDOW_MODAL);
            //stage.initOwner(superStage);       
            stage.show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}