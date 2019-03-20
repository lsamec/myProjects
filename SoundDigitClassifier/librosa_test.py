import librosa
import numpy as np
from IPython.lib.display import Audio

def invlogamplitude(S):
    return 10.0**(S/10.0)
"""librosa.logamplitude is actually 10_log10, so invert that."""



filename = "C:/Users/leonx64/Desktop/comp_intel_project/0/0Da0.wav"
y, sr = librosa.load(filename)
y_norm =librosa.util.normalize(y)

Y = librosa.stft(y_norm)
mfccs = librosa.feature.mfcc(y,n_mfcc=10).tolist()
mfccs_flat = [item for sublist in mfccs for item in sublist]
print(mfccs_flat)

"""
n_mfcc = mfccs.shape[0]
n_mel = 128
dctm = librosa.filters.dct(n_mfcc, n_mel)
n_fft = 2048
mel_basis = librosa.filters.mel(sr, n_fft)


bin_scaling = 1.0/np.maximum(0.0005, np.sum(np.dot(mel_basis.T, mel_basis),
axis=0))


recon_stft = bin_scaling[:, np.newaxis] * np.dot(mel_basis.T,
invlogamplitude(np.dot(dctm.T, mfccs)))


excitation = np.random.randn(y.shape[0])
E = librosa.stft(excitation)
recon = librosa.istft(E/np.abs(E)*np.sqrt(recon_stft))


print(Audio(recon, rate=sr).data)
"""