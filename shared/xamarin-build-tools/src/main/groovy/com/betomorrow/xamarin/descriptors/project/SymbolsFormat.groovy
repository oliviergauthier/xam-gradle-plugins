package com.betomorrow.xamarin.descriptors.project

enum SymbolsFormat {

    PDB("pdb"),
    MDB("dll.mdb")

    private String fileExtension

    SymbolsFormat(String fileExtension) {
        this.fileExtension = fileExtension
    }

    String getFileExtension() {
        return this.fileExtension
    }

}