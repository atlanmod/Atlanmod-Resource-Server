package org.atlanmod.karadoc.rmi;

import org.atlanmod.karadoc.core.Response;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiResourceServer extends Remote {

        /**
         * Find a model based on the uri
         * @param modelUri model uri
         * @return the model
         */
        Response<Resource> getModel(String modelUri) throws RemoteException;


        /**
         * Get all the resource in the resource set
         * @return the List of all resource
         */
        Response<List<Resource>> getAll() throws RemoteException;

        /**
         * Get all the model and return there uri
         *
         * @return A list of all the models uri
         */
        Response<List<String>> getModelUris() throws RemoteException;

        /**
         * Get element by name
         * @param modelUri The uri of the resource
         * @param elementname name of the object
         * @return the object found. Null of none
         */
        Response<EObject> getModelElementByName(String modelUri, String elementname) throws RemoteException;


        /**
         * Delete a model
         * @param modelUri the id the model to delete
         * @return a boolean true if the removal succeeded.
         */
        Response<Boolean> delete(String modelUri) throws RemoteException;

        /**
         * Close a model wihout saving it's content to the disk
         * @param modelUri model to close
         * @return a boolean representing the status of the operation
         */
        Response<Boolean> close(String modelUri) throws RemoteException;

        /**
         * Create a new model
         * @param modelUri the uri of the new model
         * @param modelAsJSON the new model to create as a json string. It must be an instance of the loaded metamodel
         * @return a boolean true if the model was added.
         */
        Response<Boolean> create(String modelUri, String modelAsJSON) throws RemoteException;

        /**
         * Replace a model with the new given model. Throw a runtime error if no model to replace is found
         * @param modelUri the URI of the model to replace
         * @param updatedModel the model tu use instead as a json string
         * @return a boolean true if the operation succeeded
         */
        Response<Boolean> update(String modelUri, String updatedModel) throws RemoteException;

        Response<String> edit(String modelUri, String jsonPatchAsString, String format)throws RemoteException;


        /**
         * Save model to server disk
         *
         * @param modelUri the uri of the model to save
         */
        Response<Boolean> save(String modelUri)  throws RemoteException;

        /**
         * Save all the models in the resourceset
         * @return a boolean true if all the models were saved.
         */
        Response<Boolean> saveAll() throws RemoteException;


        /**
         * Ping the model
         * @return pong
         */
        Response<Boolean> ping() throws RemoteException;


        /**
         * Unsubscribe from model
         * @param modelUri the model to unsubscribe
         * @return a boolean true if the operation succeeded
         */
        Response<Boolean> unsubscribe(String modelUri) throws RemoteException;

        /**
         * Undo last modification on the model
         * @param modelUri the uri of the model on witch to perform the undo
         * @return the modified resource
         */
        Response<Resource> undo(String modelUri) throws RemoteException;

        /**
         * Redo last modification on the model
         * @param modelUri the uri of the model on witch to perform the undo
         * @return the modified resource
         */
        Response<Resource> redo(String modelUri) throws RemoteException;


}
