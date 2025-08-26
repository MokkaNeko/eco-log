package com.mokaneko.recycle2.data.repository

import com.mokaneko.recycle2.R
import com.mokaneko.recycle2.data.model.KategoriItem
import com.mokaneko.recycle2.data.model.SubKategoriItem

object KategoriData {
    val listKategori = listOf(
        KategoriItem(
            namaKategori = "Organik",
            subKategoriList = listOf(
                SubKategoriItem(
                    "Sisa Makanan dan Dedaunan",
                    "Organik",
                    "Ini adalah jenis sampah yang paling sering menyebabkan bau tidak sedap di tempat sampah dan jika dibuang ke TPA, akan menghasilkan gas metana, yaitu gas rumah kaca yang sangat kuat. Namun, sampah organik sesungguhnya adalah sumber daya yang sangat berharga. Mengolah sampah organik di sumbernya (di rumah kita) akan mengurangi volume sampah yang diangkut ke TPA secara drastis, mengurangi bau, dan yang terpenting, mengubah \"sampah\" menjadi \"harta karun\" berupa pupuk subur untuk tanaman.\n" +
                            "\n" +
                            "Rekomendasi Pengolahan:\n\n" +
                            "1. Pengomposan (Composting) - Solusi Terbaik & Paling Bermanfaat\n" +
                            "Prinsip dasar pengomposan adalah menyeimbangkan dua elemen:\n" +
                            "\"Bahan Hijau\" (Kaya Nitrogen): Sisa makanan, sisa sayur/buah, dan rumput segar.\n\n" +
                            "\"Bahan Cokelat\" (Kaya Karbon): Dedaunan kering, ranting kecil, serutan kayu, sobekan kardus/kertas.\n\n" +
                            "Berikut beberapa metode yang bisa dipilih, sangat cocok untuk kondisi:\n" +
                            "Metode 1: Lubang Resapan Biopori (Sangat Praktis untuk Rumah Berpekarangan)\n" +
                            "Gunakan bor tanah atau linggis untuk membuat lubang di tanah pekarangan dengan kedalaman sekitar 80-100 cm.\n" +
                            "Masukkan semua sampah organik harian (sisa dapur dan daun) ke dalam lubang tersebut.\n" +
                            "Tutup lubang dengan penutup yang berongga (misalnya paving block atau tutup semen).\n" +
                            "Biarkan sampah terurai secara alami di dalam tanah. Ini tidak hanya mengolah sampah, tetapi juga menyuburkan tanah di sekitarnya dan membantu penyerapan air hujan.\n\n" +
                            "Metode 2: Komposter Ember Tumpuk (Untuk Lahan Terbatas)\n" +
                            "Siapkan dua buah ember cat bekas yang memiliki tutup.\n" +
                            "Lubangi dasar ember bagian atas dengan banyak lubang kecil. Pasang keran di bagian bawah ember bawah.\n" +
                            "Tumpuk kedua ember. Ember atas untuk menampung sampah, ember bawah untuk menampung air lindi (cairan pupuk).\n" +
                            "Masukkan sisa makanan dan dedaunan kering secara berlapis ke ember atas. Tutup rapat.\n" +
                            "Dalam beberapa minggu, Anda akan mendapatkan pupuk padat (kompos) di ember atas dan pupuk organik cair (POC) dari keran di ember bawah.\n\n" +
                            "Metode 3: Penumpukan Terbuka (Untuk Sampah Kebun yang Melimpah)\n" +
                            "Pilih sudut di pekarangan Anda.\n" +
                            "Buat tumpukan dengan prinsip berlapis: satu lapis daun kering/ranting (\"cokelat\"), lalu satu lapis sisa dapur/rumput basah (\"hijau\").\n" +
                            "Jaga agar tumpukan tetap lembab (tidak becek, tidak kering) dan aduk tumpukan setiap satu atau dua minggu sekali untuk mempercepat proses.\n" +
                            "Dalam beberapa bulan, tumpukan tersebut akan berubah menjadi kompos berwarna gelap yang kaya nutrisi.\n\n" +
                            "2. Pakan Ternak\n" +
                            "Jika Anda atau tetangga Anda memelihara ternak seperti ayam, bebek, mentok, atau kambing, sisa sayuran (yang belum busuk) bisa menjadi pakan tambahan yang sangat baik.",
                    R.drawable.ic_kategori_dedaunan),
                SubKategoriItem(
                    "Kayu",
                    "Organik",
                    "Sampah kayu adalah semua jenis limbah yang berasal dari kayu, baik kayu utuh maupun olahannya. Kayu adalah material organik yang sangat berharga. Membuangnya ke TPA (Tempat Pemrosesan Akhir) sangat disayangkan karena ia memiliki banyak potensi untuk dimanfaatkan kembali, baik sebagai produk baru maupun sumber energi.\n" +
                            "\n" +
                            "Rekomendasi Pengolahan:\n\n" +
                            "1. Furnitur dan Dekorasi Rumah\n" +
                            "Rak Dinding (Ambalan): Potongan papan atau papan palet bekas bisa dihaluskan (diamplas), dicat, atau divernis untuk dijadikan rak dinding yang estetik.\n" +
                            "Meja Kopi atau Kursi Kecil: Potongan kayu balok yang cukup besar bisa dijadikan kaki meja atau kursi stul (dingklik) yang unik. Palet kayu bisa disusun dan digabungkan menjadi meja atau sofa bergaya rustic.\n" +
                            "Hiasan Dinding: Ranting-ranting kering bisa dirangkai menjadi hiasan dinding, bingkai cermin, atau gantungan aksesoris. Potongan kayu kecil (wood slice) bisa ditempel di dinding sebagai aksen dekoratif.\n" +
                            "2. Barang Fungsional\n\n" +
                            "Tatakan Gelas atau Panci Panas: Potongan kayu dahan yang dipotong melintang (wood slice coaster) bisa menjadi tatakan gelas yang cantik.\n" +
                            "Gantungan Baju atau Kunci: Sebuah papan kayu bekas yang dipasangi beberapa pengait atau bahkan potongan dahan kecil yang kokoh bisa menjadi gantungan serbaguna.",
                    R.drawable.ic_kategori_kayu
                )
            )
        ),
        KategoriItem(
            namaKategori = "Anorganik",
            subKategoriList = listOf(
                SubKategoriItem(
                    "Botol",
                    "Anorganik",
                    "Botol adalah wadah berbentuk silinder yang biasa digunakan untuk menyimpan minuman, bumbu, minyak, dan produk rumah tangga lainnya. Botol bisa terbuat dari plastik (seperti PET) atau kaca.\n" +
                            "\n" +
                            "Rekomendasi Pengolahan:\n\n" +
                            "1. Gunakan Kembali (Reuse)\n" +
                            "Botol plastik bisa digunakan ulang untuk:\n" +
                            "Tempat sabun cair, minyak goreng isi ulang, atau pupuk cair.\n" +
                            "Wadah bumbu dapur atau kerajinan air mancur mini.\n" +
                            "Botol kaca bisa digunakan sebagai:\n" +
                            "Vas bunga, toples rempah, atau tempat lilin dekoratif.\n" +
                            "\n" +
                            "2. Daur Ulang Kreatif (Upcycle)\n" +
                            "Botol plastik:\n" +
                            "Dibuat pot tanaman gantung atau vertikal garden.\n" +
                            "Dibuat celengan, tempat alat tulis, atau mainan anak.\n" +
                            "Botol kaca:\n" +
                            "Dijadikan lampu hias botol dengan lampu LED di dalamnya.\n" +
                            "Lukis permukaan botol untuk jadi dekorasi rumah.\n" +
                            "\n" +
                            "3. Pisahkan dan Simpan\n" +
                            "Cuci dan keringkan sebelum disimpan.\n" +
                            "Simpan berdasarkan jenis (plastik atau kaca) agar mudah didaur ulang atau dijual ke pengepul.\n" +
                            "\n" +
                            "4. Jual ke Bank Sampah\n" +
                            "Banyak bank sampah menerima botol plastik dan kaca untuk didaur ulang secara industri.\n",
                    R.drawable.ic_kategori_botol),
                SubKategoriItem(
                    "Kaca",
                    "Anorganik",
                    "Sampah kaca mencakup material berbahan dasar kaca seperti gelas minum, toples kaca, piring kaca, dan pecahan kaca. Kaca tidak terurai secara alami dan membutuhkan pengolahan khusus agar tidak membahayakan lingkungan atau manusia.\n" +
                            "\n" +
                            "Rekomendasi Pengolahan:\n\n" +
                            "1. Gunakan Kembali (Reuse)\n" +
                            "Toples kaca bekas bisa digunakan sebagai:\n" +
                            "Wadah bumbu dapur, makanan kering, atau perlengkapan jahit.\n" +
                            "Tempat lilin aromaterapi atau tanaman kaktus kecil (succulent jar).\n" +
                            "Gelas bekas bisa dijadikan:\n" +
                            "Pot kecil untuk tanaman, tempat pensil, atau wadah sendok-garpu.\n" +
                            "\n" +
                            "2. Daur Ulang Kreatif (Upcycle)\n" +
                            "Kaca bening atau berwarna bisa dijadikan:\n" +
                            "Hiasan taman dengan teknik mosaik (dari pecahan kaca).\n" +
                            "Permukaan meja hias atau bingkai foto bertekstur.\n" +
                            "Gantungan dinding dari pecahan kaca yang dibentuk (dengan hati-hati dan alat pelindung).\n" +
                            "\n" +
                            "3. Penanganan Aman Pecahan Kaca\n" +
                            "Bungkus pecahan kaca dengan koran/kemasan tebal dan beri label “pecah belah” sebelum dibuang.\n" +
                            "Jika memungkinkan, kumpulkan pecahan kaca untuk diserahkan ke bank sampah atau pengepul khusus kaca.\n" +
                            "\n" +
                            "4. Edukasi & Pencegahan\n" +
                            "Ajarkan anggota keluarga untuk tidak langsung membuang kaca ke tempat sampah biasa.\n" +
                            "Prioritaskan penggunaan wadah yang dapat digunakan berulang kali untuk mengurangi sampah kaca sekali pakai.\n",
                    R.drawable.ic_kategori_kaca),
                SubKategoriItem(
                    "Kaleng",
                    "Anorganik",
                    "Sampah kaleng adalah wadah yang terbuat dari logam (umumnya aluminium atau baja/timah) yang sudah tidak terpakai dan dibuang. Meskipun dapat didaur ulang dengan baik, sampah kaleng yang tercecer di lingkungan juga membutuhkan waktu lama untuk terurai dan bisa menyebabkan karat.\n" +
                            "\n" +
                            "Rekomendasi Pengolahan:\n\n" +
                            "1. Penyimpanan dan Organisasi\n" +
                            "Tempat Alat Tulis/Perkakas: Kaleng susu kental manis, sarden, atau biskuit yang sudah dibersihkan bisa langsung dicat atau dihias menjadi tempat pensil, spidol, kuas, atau perkakas kecil seperti obeng dan tang, dan juga wadah bumbu dapur.\n\n" +
                            "2. Mainan dan Edukasi\n" +
                            "Robot-robotan atau Kendaraan Mini: Dengan menggabungkan beberapa kaleng berbagai ukuran dan bahan tambahan, anak-anak bisa berkreasi membuat mainan.\n" +
                            "Alat Musik Sederhana: Kaleng bisa diubah menjadi drum kecil atau tamborin sederhana dengan menambahkan bahan lain.\n\n" +
                            "3. Fungsional dan Dekorasi lainnya\n" +
                            "Pot Tanaman: Kaleng bekas cat atau makanan yang lebih besar bisa dilubangi bagian bawahnya dan dicat ulang menjadi pot tanaman yang unik dan industrial.\n" +
                            "Asbak: Kaleng kecil bisa diubah fungsi menjadi asbak.\n",
                    R.drawable.ic_kategori_kaleng),
                SubKategoriItem(
                    "Kantong Plastik",
                    "Anorganik",
                    "Sampah kantong plastik, atau yang sering disebut \"kresek\", adalah kantong yang terbuat dari plastik tipis (biasanya jenis HDPE atau LDPE) yang sudah tidak digunakan lagi. Sampah kantong plastik sangat sulit terurai secara alami dan dapat terfragmentasi menjadi mikroplastik yang berbahaya bagi ekosistem dan kesehatan.\n" +
                            "\n" +
                            "Rekomendasi Pengolahan:\n\n" +
                            "1. Reuse\n" +
                            "Gunakan kembali sebanyak mungkin untuk keperluan lain. Misalnya, sebagai kantong sampah di rumah, pembungkus barang agar tidak kotor, atau untuk membawa barang pada kesempatan berikutnya.\n" +
                            "\n2. Kreasi Produk dan Pemanfaatan Material\n" +
                            "Kerajinan Anyaman: Kantong plastik dapat dipotong memanjang seperti tali, kemudian dianyam atau dirajut menjadi produk seperti tas, dompet, alas duduk, atau hiasan dinding.\n" +
                            "Bahan Isian: Gunakan sebagai bahan isian untuk bantal atau guling hias (bukan untuk tidur) dan boneka.\n" +
                            "Lapis Anti Air: Manfaatkan sebagai lapisan dalam untuk pot tanaman atau proyek kerajinan lain yang membutuhkan lapisan tahan air.\n",
                    R.drawable.ic_kategori_plastik),
                SubKategoriItem(
                    "Kardus",
                    "Anorganik",
                    "Sampah kardus adalah limbah yang umumnya digunakan sebagai kemasan pelindung untuk pengiriman barang, kotak sepatu, atau wadah produk elektronik dan makanan. Meskipun mudah terurai secara alami dibandingkan plastik, menumpuknya sampah kardus di TPA (Tempat Pemrosesan Akhir) tetap menyia-nyiakan sumber daya yang sebenarnya sangat bisa dimanfaatkan kembali.\n" +
                            "\n" +
                            "Rekomendasi Pengolahan:\n\n" +
                            "1. Kreasi Produk\n" +
                            "Kotak Penyimpanan Dekoratif: Lapisi kotak kardus bekas (misalnya kotak sepatu) dengan kertas kado, kain, atau cat untuk menjadikannya kotak penyimpanan estetik untuk dokumen, mainan, atau pernak-pernik.\n" +
                            "Partisi Laci/Lemari: Potong lembaran kardus sesuai ukuran laci untuk membuat sekat-sekat organizer yang rapi.\n" +
                            "Kendaraan & Kostum: Bentuk menjadi mobil-mobilan, pesawat, topeng, atau kostum robot yang bisa diwarnai dan dihias bersama anak.\n" +
                            "Puzzle & Diorama: Gambar pola pada kardus lalu potong untuk membuat puzzle sederhana, atau gunakan sebagai dasar untuk membuat diorama (maket tiga dimensi) untuk tugas sekolah.\n" +
                            "Alas Melukis/Berkegiatan: Gunakan lembaran kardus lebar sebagai alas agar lantai tidak kotor saat anak-anak melukis atau melakukan aktivitas lainnya.\n" +
                            "Bingkai Foto: Potong dan hias kardus menjadi bingkai foto yang unik dan ringan.\n\n" +
                            "2. Kompos\n" +
                            "Jika Anda memiliki komposter, cacahan kardus (yang tidak dilapisi plastik/lilin dan tanpa tinta berlebihan) bisa menjadi sumber karbon atau \"bahan cokelat\" yang sangat baik. Ini membantu menyeimbangkan \"bahan hijau\" (sisa sayur/buah) dan menjaga komposter tidak terlalu basah atau bau.\n",
                    R.drawable.ic_kategori_kardus),
                SubKategoriItem(
                    "Kemasan Plastik",
                    "Anorganik",
                    "Sampah kemasan plastik merupakan sampah tempat atau wadah yang terbuat dari plastik. Karena sampah ini terbuat dari plastik, maka sampah ini sangat sulit terurai secara alami dan dapat terfragmentasi menjadi mikroplastik yang berbahaya bagi ekosistem dan kesehatan.\n" +
                            "\n" +
                            "Rekomendasi Pengolahan:\n\n" +
                            "1. Daur Ulang\n" +
                            "Pilah berdasarkan Jenis: Perhatikan kode segitiga di bawah kemasan (misalnya HDPE, PP). Pisahkan wadah plastik yang kaku (seperti jeriken atau wadah margarin) dari sampah lain.\n\n" +
                            "2. Kreasi Produk\n" +
                            "Isian Produk: Potongan kecil kemasan plastik bersih bisa dijadikan bahan isian untuk kerajinan tangan seperti bantal kursi hias (bukan bantal tidur) atau boneka.\n\n" +
                            "3. Ecobrick\n" +
                            "Ini adalah solusi cerdas khususnya untuk sampah plastik bernilai rendah yang tidak bisa didaur ulang (seperti saset, bungkus permen, dan plastik tipis lainnya).\n" +
                            "Caranya adalah dengan membersihkan, mengeringkan, lalu memadatkan sampah-sampah plastik tersebut ke dalam botol plastik hingga sangat keras. Ecobrick ini kemudian bisa dirangkai menjadi meja, kursi, atau bahkan material bangunan modular. Ini adalah cara untuk \"mengunci\" sampah agar tidak mencemari lingkungan.\n",
                    R.drawable.ic_kategori_kemasan),
                SubKategoriItem(
                    "Kertas",
                    "Anorganik",
                    "Sampah kertas adalah semua jenis limbah yang berasal dari material kertas yang sudah tidak terpakai. Kertas adalah bahan organik yang dapat terurai, namun membuangnya begitu saja berarti menyia-nyiakan sumber daya serat kayu yang berharga dan dapat diolah kembali.\n" +
                            "\n" +
                            "Rekomendasi Pengolahan:\n\n" +
                            "1. Seni dan Kerajinan Tangan\n" +
                            "Bubur Kertas: Rendam sobekan kertas (terutama koran) dalam air, hancurkan hingga menjadi bubur, lalu campur dengan lem. Adonan ini bisa dicetak menjadi topeng, patung, mangkuk hias, atau berbagai bentuk tiga dimensi lainnya yang setelah kering akan menjadi keras dan ringan.\n" +
                            "Kolase & Mozaik: Manfaatkan potongan kertas berwarna dari majalah atau brosur untuk membuat gambar kolase atau mozaik pada kanvas atau permukaan lain.\n\n" +
                            "2. Fungsional Lain\n" +
                            "Pembungkus Kado Alternatif: Gunakan halaman majalah yang menarik, koran bekas, atau kantong kertas bersih sebagai pembungkus kado yang unik dan ramah lingkungan.\n" +
                            "Isian Paket: Remas-remas kertas bekas untuk dijadikan bantalan atau isian pelindung saat mengirim barang.\n" +
                            "Alas atau Pembungkus: Gunakan lembaran koran bekas sebagai alas laci, alas saat mengecat, atau untuk membungkus dan mematangkan buah seperti mangga atau alpukat.\n\n" +
                            "3. Daur Ulang\n" +
                            "Ini adalah metode pengelolaan sampah kertas yang paling umum dan efisien. Pastikan kertas dalam kondisi kering dan tidak terkontaminasi minyak atau sisa makanan.\n" +
                            "Pilah: Pisahkan kertas dari sampah basah dan kontaminan lainnya. Struk belanja (kertas termal) dan kertas yang berlapis plastik/lilin umumnya sulit didaur ulang.\n" +
                            "Setor ke Bank Sampah: Kumpulkan kertas yang sudah dipilah dan setorkan ke bank sampah atau pengepul. Di pabrik, kertas akan diolah menjadi bubur kertas (pulp) untuk dijadikan berbagai produk kertas baru, mulai dari kertas koran, kertas toilet, hingga kardus.\n" +
                            "\n4. Kompos\n" +
                            "Sobekan kertas yang tidak berlapis plastik (seperti koran atau kertas HVS tanpa tinta berlebihan) adalah sumber karbon (\"bahan cokelat\") yang sangat baik untuk pengomposan. Campurkan dengan sampah organik \"hijau\" (sisa sayur/buah) untuk menghasilkan kompos yang seimbang dan berkualitas.",
                    R.drawable.ic_kategori_kertas),
                SubKategoriItem(
                    "Logam",
                    "Anorganik",
                    "Sampah logam adalah semua jenis limbah berbahan dasar logam. Kategori ini sangat beragam dan sering disebut sebagai \"besi tua\" atau rongsokan. Limbah jenis ini memiliki nilai ekonomi yang signifikan dan merupakan salah satu komoditas utama dalam industri daur ulang.\n" +
                            "\n" +
                            "Rekomendasi Pengolahan:\n\n" +
                            "1. Barang Fungsional\n" +
                            "Rak Dinding: Rangkai potongan besi atau pipa menjadi rak buku atau rak pajangan dengan gaya industrial yang maskulin.\n" +
                            "Gantungan Baju/Perkakas: Pipa bekas atau bahkan kunci-kunci pas bekas yang dilas menjadi satu bisa menjadi gantungan yang fungsional dan artistik.\n" +
                            "Seni Patung Logam: Ini adalah level kreativitas tertinggi dimana berbagai rongsokan logam seperti mur, baut, rantai, busi, laher, dan potongan plat disatukan (biasanya dengan teknik las) untuk membentuk patung hewan, robot, figur manusia, atau karya seni abstrak.\n" +
                            "\n2. Pengolahan Lain yang Direkomendasikan\n" +
                            "Jual ke pengepul barang bekas, Limbah ini memiliki nilai jual yang jauh lebih tinggi dibandingkan sampah lainnya.",
                    R.drawable.ic_kategori_logam
                )
            )
        ),
        KategoriItem(
            namaKategori = "B3",
            subKategoriList = listOf(
                SubKategoriItem(
                    "Baterai dan Aki",
                    "B3",
                    "Baterai dan aki mengandung bahan kimia beracun seperti merkuri, timbal, dan asam sulfat. Jika dibuang sembarangan, zat ini bisa mencemari tanah, air, dan membahayakan kesehatan manusia maupun hewan.\n" +
                            "\n" +
                            "Rekomendasi Pengolahan:\n\n" +
                            "1. Jangan Dibuang ke Tempat Sampah Biasa\n" +
                            "Baterai dan aki tidak boleh dibuang bersama sampah rumah tangga karena beracun dan berbahaya.\n" +
                            "\n" +
                            "2. Kumpulkan dan Simpan Terpisah\n" +
                            "Gunakan wadah khusus (bekas toples plastik/kaca) untuk mengumpulkan baterai bekas.\n" +
                            "Simpan di tempat kering, jauh dari jangkauan anak-anak dan hewan peliharaan.\n" +
                            "\n" +
                            "3. Serahkan ke Tempat Penampungan Khusus\n" +
                            "Bawa ke:\n" +
                            "Drop box baterai bekas di minimarket besar, toko elektronik, atau kantor layanan publik.\n" +
                            "Dinas lingkungan hidup setempat yang memiliki program pengumpulan limbah B3.\n" +
                            "Bank Sampah Induk yang menerima limbah B3 secara terpisah.\n" +
                            "\n" +
                            "4. Alternatif: Gunakan Produk Isi Ulang\n" +
                            "Gunakan baterai rechargeable (isi ulang) untuk mengurangi sampah baterai sekali pakai.\n" +
                            "Pilih aki ramah lingkungan yang bisa dikembalikan ke toko saat diganti.\n" +
                            "\n" +
                            "5. Edukasi Keluarga\n" +
                            "Edukasi keluarga untuk tidak membakar, membelah, atau membuang baterai/aki ke sungai, got, atau tanah terbuka.",
                    R.drawable.ic_kategori_baterai),
                SubKategoriItem(
                    "Sampah Elektronik",
                    "B3",
                    "Sampah elektronik mencakup perangkat seperti komputer, televisi, laptop, handphone, dan peralatan elektronik lainnya yang sudah rusak atau tidak terpakai. Komponen dalam e-waste mengandung logam berat dan bahan kimia beracun seperti timbal, merkuri, dan kadmium yang membahayakan jika dibuang sembarangan.\n" +
                            "\n" +
                            "Rekomendasi Pengolahan:\n\n" +
                            "1. Simpan Terpisah dan Jangan Buang ke Tempat Sampah Biasa\n" +
                            "Pisahkan dari sampah lainnya, simpan dalam kondisi kering dan aman.\n" +
                            "\n" +
                            "2. Manfaatkan Layanan Tukar Tambah atau Penarikan Barang Lama\n" +
                            "Banyak toko elektronik atau produsen memiliki program tukar tambah atau pengambilan e-waste.\n" +
                            "Contoh: Toko HP menerima HP lama untuk diskon pembelian baru.\n" +
                            "\n" +
                            "3. Donasikan jika Masih Layak Pakai\n" +
                            "Komputer/laptop/TV yang masih berfungsi bisa disumbangkan ke:\n" +
                            "Sekolah, komunitas belajar, atau individu yang membutuhkan.\n" +
                            "Program donasi elektronik dari LSM atau komunitas teknologi.\n" +
                            "\n" +
                            "4. Jual ke Pengepul atau Daur Ulang Khusus E-Waste\n" +
                            "Banyak pengepul khusus e-waste yang bisa mengambil dan memisahkan komponen bernilai (tembaga, logam mulia).\n" +
                            "Hubungi Dinas Lingkungan Hidup setempat atau cari komunitas pengelola e-waste.\n" +
                            "\n" +
                            "5. Hindari Membongkar Sendiri\n" +
                            "Jangan membongkar perangkat elektronik sendiri tanpa alat dan pengetahuan karena berisiko terpapar bahan berbahaya.\n" +
                            "\n" +
                            "6. Edukasi Keluarga\n" +
                            "Ajarkan pentingnya menyimpan dan membuang e-waste dengan benar agar tidak mencemari lingkungan.",
                    R.drawable.ic_kategori_elekrtonik
                )
            )
        )
    )

    fun findSubkategori(subkategoriName: String): SubKategoriItem? {
        return listKategori
            .flatMap { it.subKategoriList }
            .find { it.namaSubKategori.equals(subkategoriName, ignoreCase = true) }
    }
}