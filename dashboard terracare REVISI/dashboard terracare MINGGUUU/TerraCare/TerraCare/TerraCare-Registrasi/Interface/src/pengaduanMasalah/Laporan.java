package pengaduanMasalah;

public class Laporan {
    private String jenisPencemaran;
    private String lokasi;
    private String deskripsi;
    private String status;

    public Laporan(String jenisPencemaran, String lokasi, String deskripsi, String status) {
        this.jenisPencemaran = jenisPencemaran;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.status = status;
    }

    // Getter methods
    public String getJenisPencemaran() {
        return jenisPencemaran;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getStatus() {
        return status;
    }

    // Setter method for status
    public void setStatus(String status) {
        this.status = status;
    }
}
