#!/bin/bash

TEST_SCRIPTS=(
  "crossSellingRecomendations.sh"
  "customer.sh"
  "discountCoupon.sh"
  "loyaltyCard.sh"
  "ollama.sh"
  "purchase.sh"
  "selledProductRecommendation.sh"
  "shops.sh"
)

run_tests() {
  echo "🚀 Rodando todos os testes..."

  for script in "${TEST_SCRIPTS[@]}"; do
    if [[ -x "./$script" ]]; then
      echo "▶️ Executando $script"
      ./"$script"
    else
      echo "⚠️ Script $script não encontrado ou não é executável."
    fi
  done
}

echo "🔧 Atualizando DNS com update-dns.sh"
./update-dns.sh

run_tests

echo "🔧 Atualizando DNS para usar Kong com use_kong.sh"
./use_kong.sh

run_tests

echo "✅ Todos os testes executados antes e depois da mudança para Kong!"
