package com.example.myapp017vanocni

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

/**
 * Tento objekt (Serializer) je klíčovou součástí DataStore, pokud používáme Protocol Buffers.
 * Jeho úkolem je "přeložit" náš datový objekt (WishList) do formátu, který lze uložit do souboru,
 * a naopak.
 */
object WishListSerializer : Serializer<WishList> {
    /**
     * Definuje výchozí hodnotu, která se použije, pokud soubor s daty ještě neexistuje.
     * Zde vracíme prázdnou instanci WishList, což znamená prázdný seznam přání.
     */
    override val defaultValue: WishList = WishList.getDefaultInstance()

    /**
     * Tato metoda říká, jak se mají data číst ze souboru (InputStream).
     * @param input Proud dat přicházející ze souboru na disku.
     * @return Vrací načtený a zparsovaný objekt WishList.
     * @throws CorruptionException Pokud jsou data v souboru poškozená a nelze je přečíst.
     */
    override suspend fun readFrom(input: InputStream): WishList {
        try {
            // Pokusíme se zparsovat (přečíst) data ze vstupního proudu podle struktury definované v .proto souboru.
            return WishList.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            // Pokud se parsování nepodaří (např. data jsou poškozená nebo v jiném formátu),
            // vyhodíme CorruptionException. DataStore tuto výjimku zachytí a použije .catch operátor,
            // který jsme definovali ve WishListFragment.
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    /**
     * Tato metoda říká, jak se mají data zapsat do souboru (OutputStream).
     * @param t Objekt WishList, který chceme uložit.
     * @param output Proud dat směřující do souboru na disku.
     */
    override suspend fun writeTo(t: WishList, output: OutputStream) = t.writeTo(output)
}
