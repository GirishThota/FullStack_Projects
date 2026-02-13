import argparse
from pathlib import Path
from joblib import load


def main():
    parser = argparse.ArgumentParser(description="Predict role category from resume text (NLP basics).")
    parser.add_argument("--model", default=str(Path(__file__).resolve().parents[1] / "model.joblib"))
    parser.add_argument("--text", required=False, help="Resume text to classify")
    parser.add_argument("--file", required=False, help="Path to a text file containing resume content")
    args = parser.parse_args()

    if not args.text and not args.file:
        raise SystemExit("Provide --text "..." OR --file resume.txt")

    text = args.text
    if args.file:
        text = Path(args.file).read_text(encoding="utf-8", errors="ignore")

    model = load(args.model)
    pred = model.predict([text])[0]

    if hasattr(model, "predict_proba"):
        proba = model.predict_proba([text])[0]
        labels = list(model.classes_)
        top = sorted(zip(labels, proba), key=lambda x: x[1], reverse=True)[:3]
        print("Top predictions:")
        for lbl, p in top:
            print(f"- {lbl}: {p:.2f}")

    print(f"\nPredicted Category: {pred}")


if __name__ == "__main__":
    main()
