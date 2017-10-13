'use babel';

import EditorPluginAtomView from './editor-plugin-atom-view';
import { CompositeDisposable } from 'atom';

export default {

  editorPluginAtomView: null,
  modalPanel: null,
  subscriptions: null,

  activate(state) {
    this.editorPluginAtomView = new EditorPluginAtomView(state.editorPluginAtomViewState);
    this.modalPanel = atom.workspace.addModalPanel({
      item: this.editorPluginAtomView.getElement(),
      visible: false
    });

    // Events subscribed to in atom's system can be easily cleaned up with a CompositeDisposable
    this.subscriptions = new CompositeDisposable();

    // Register command that toggles this view
    this.subscriptions.add(atom.commands.add('atom-workspace', {
      'editor-plugin-atom:toggle': () => this.toggle()
    }));
  },

  deactivate() {
    this.modalPanel.destroy();
    this.subscriptions.dispose();
    this.editorPluginAtomView.destroy();
  },

  serialize() {
    return {
      editorPluginAtomViewState: this.editorPluginAtomView.serialize()
    };
  },

  toggle() {
    console.log('EditorPluginAtom was toggled!');
    return (
      this.modalPanel.isVisible() ?
      this.modalPanel.hide() :
      this.modalPanel.show()
    );
  }

};
